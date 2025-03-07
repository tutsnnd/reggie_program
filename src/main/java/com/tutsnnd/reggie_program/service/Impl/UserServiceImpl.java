package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.mapper.UserMapper;
import com.tutsnnd.reggie_program.pojo.User;
import com.tutsnnd.reggie_program.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
