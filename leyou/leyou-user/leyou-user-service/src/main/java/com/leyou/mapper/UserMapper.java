package com.leyou.mapper;

import org.apache.ibatis.annotations.Param;
import com.leyou.user.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 要子康
 * @description UserMapper
 * @since 2020/7/18 14:52
 */

public interface UserMapper extends Mapper<User> {

    @Select("SELECT phone FROM tb_user WHERE phone = #{phone}")
    String selectByPhone(@Param("phone") String phone);
}


