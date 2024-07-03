package org.developtemplate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.developtemplate.common.biz.UserContext;
import org.developtemplate.common.convention.errorcode.BaseErrorCode;
import org.developtemplate.common.convention.exception.ClientException;
import org.developtemplate.common.convention.exception.ServiceException;
import org.developtemplate.common.enums.UserErrorCodeEnum;
import org.developtemplate.domain.dto.request.UserLoginRequestDTO;
import org.developtemplate.domain.dto.request.UserRegisterRequestDTO;
import org.developtemplate.domain.dto.request.UserUpdateRequestDTO;
import org.developtemplate.domain.dto.response.UserInfoResponseDTO;
import org.developtemplate.domain.dto.response.UserLoginResponseDTO;
import org.developtemplate.domain.entity.UserDO;
import org.developtemplate.mapper.UserMapper;
import org.developtemplate.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.developtemplate.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static org.developtemplate.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static org.developtemplate.common.constant.ServiceConstant.SALT;
import static org.developtemplate.common.convention.errorcode.BaseErrorCode.CLIENT_ERROR;
import static org.developtemplate.common.convention.errorcode.BaseErrorCode.SERVICE_ERROR;
import static org.developtemplate.common.enums.UserErrorCodeEnum.*;


/**
 * 用户服务层接口实现类
 *
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    /**
     * 用户注册缓存布隆过滤器
     */
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void registerUser(UserRegisterRequestDTO requestParam) {
        if (requestParam == null) {
            throw new ClientException(CLIENT_ERROR);
        }
        String userAccount = requestParam.getUserAccount();
        String userPassword = requestParam.getUserPassword();
        String checkPassword = requestParam.getCheckPassword();

        // 1、校验参数
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new ClientException(CLIENT_ERROR);
        }
        if (Boolean.FALSE.equals(checkUsername(userAccount))) {
            throw new ClientException(USER_NAME_EXIST);
        }

        if (!userPassword.equals(checkPassword)) {
            throw new ClientException(BaseErrorCode.PASSWORD_VALIDATION_FAILED_ERROR);
        }

        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userAccount);
        if (!lock.tryLock()) {
            throw new ClientException(USER_NAME_EXIST);
        }
        try {
            // 2. 密码加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            UserDO user = new UserDO();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            int inserted = baseMapper.insert(user);

            if (inserted < 1) {
                throw new ClientException(USER_SAVE_ERROR);
            }
            // 布隆过滤器添加用户名
            userRegisterCachePenetrationBloomFilter.add(userAccount);
        } catch (DuplicateKeyException ex) {
            throw new ClientException(USER_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO requestParam) {
        if (requestParam == null) {
            throw new ClientException(CLIENT_ERROR);
        }

        String userAccount = requestParam.getUserAccount();
        String userPassword = requestParam.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new ClientException(CLIENT_ERROR);
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUserAccount, userAccount)
                .eq(UserDO::getUserPassword, encryptPassword)
                .eq(UserDO::getIsDelete, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(USER_NULL);
        }

        Map<Object, Object> hasLoginMap = stringRedisTemplate.opsForHash().entries(USER_LOGIN_KEY + userAccount);
        if (CollUtil.isNotEmpty(hasLoginMap)) {
            // 重复登录刷新token
            stringRedisTemplate.expire(USER_LOGIN_KEY + userAccount, 30L, TimeUnit.MINUTES);
            String token = hasLoginMap.keySet().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElseThrow(() -> new ClientException(CLIENT_ERROR));
            return new UserLoginResponseDTO(token);
        }

        /*
         * Hash
         * Key：login_用户名
         * Value：
         *      Key：token标识
         *      Val：JSON 字符串（用户信息）
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + userAccount, uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire(USER_LOGIN_KEY + userAccount, 30L, TimeUnit.MINUTES);
        return new UserLoginResponseDTO(uuid);
    }

    @Override
    public Boolean checkUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public UserInfoResponseDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUserAccount, username)
                .eq(UserDO::getIsDelete, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserInfoResponseDTO result = new UserInfoResponseDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public void updateUser(UserUpdateRequestDTO requestParam) {
        if(requestParam == null || requestParam.getId() == null){
            throw new ClientException(CLIENT_ERROR);
        }

        UserDO user = new UserDO();
        BeanUtils.copyProperties(requestParam, user);
        int result = baseMapper.updateById(user);
        if(result < 1){
            throw new ServiceException(SERVICE_ERROR);
        }
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    @Override
    public void logoutUser(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete(USER_LOGIN_KEY + username);
            return;
        }
        throw new ClientException(USER_NOT_LOGIN);
    }

}
