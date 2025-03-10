package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.mapper.ShoppingCartMapper;
import com.tutsnnd.reggie_program.pojo.ShoppingCart;
import com.tutsnnd.reggie_program.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
