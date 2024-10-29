package org.developtemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.developtemplate.domain.dto.request.UserLoginRequestDTO;
import org.developtemplate.domain.dto.request.UserRegisterRequestDTO;
import org.developtemplate.domain.dto.request.UserUpdateRequestDTO;
import org.developtemplate.domain.dto.response.UserInfoResponseDTO;
import org.developtemplate.domain.dto.response.UserLoginResponseDTO;
import org.developtemplate.domain.entity.UserDO;


/**
 * 用户服务层接口
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
public interface UserService extends IService<UserDO> {
    /**
     * 注册用户
     * @param requestParam 用户注册请求参数
     */
    void registerUser(UserRegisterRequestDTO requestParam);

    /**
     * 登录用户
     * @param requestParam 用户登录请求参数
     * @return 用户登录相应参数
     */
    UserLoginResponseDTO loginUser(UserLoginRequestDTO requestParam);

    /**
     * 查询用户名是否存在
     *
     * @param username 用户名
     * @return 用户名存在返回 True，不存在返回 False
     */
    Boolean checkUsername(String username);

    /**
     * 根据用户id获取用户名
     * @param id 用户id
     * @return 用户名
     */
    String getUserNameById(Long id);

    /**
     * 查询用户信息
     * @param username 用户名
     * @return 用户信息响应参数
     */
    UserInfoResponseDTO getUserByUsername(String username);

    /**
     * 根据用户名修改用户
     *
     * @param requestParam 修改用户请求参数
     */
    void updateUser(UserUpdateRequestDTO requestParam);

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    用户登录 Token
     * @return 用户是否登录标识
     */
    Boolean checkLogin(String username, String token);

    /**
     * 退出登录
     *
     * @param username 用户名
     * @param token    用户登录 Token
     */
    void logoutUser(String username, String token);
}
