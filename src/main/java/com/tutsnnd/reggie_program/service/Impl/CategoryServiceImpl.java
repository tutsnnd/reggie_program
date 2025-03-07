package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.mapper.CategoryMapper;
import com.tutsnnd.reggie_program.pojo.Category;
import com.tutsnnd.reggie_program.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
