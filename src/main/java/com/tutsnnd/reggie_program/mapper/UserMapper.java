package com.tutsnnd.reggie_program.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutsnnd.reggie_program.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
