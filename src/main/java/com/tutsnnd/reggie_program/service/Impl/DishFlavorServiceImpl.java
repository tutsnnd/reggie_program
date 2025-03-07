package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.mapper.DishFlavorMapper;
import com.tutsnnd.reggie_program.pojo.DishFlavor;
import com.tutsnnd.reggie_program.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
