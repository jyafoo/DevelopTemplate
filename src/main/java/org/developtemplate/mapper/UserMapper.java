package org.developtemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.developtemplate.domain.entity.UserDO;


/**
 * 用户持久层接口
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}




