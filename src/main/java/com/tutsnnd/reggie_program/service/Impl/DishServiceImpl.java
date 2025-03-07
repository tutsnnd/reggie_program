package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.Dto.DishDto;
import com.tutsnnd.reggie_program.mapper.DishMapper;
import com.tutsnnd.reggie_program.pojo.Dish;
import com.tutsnnd.reggie_program.pojo.DishFlavor;
import com.tutsnnd.reggie_program.service.CategoryService;
import com.tutsnnd.reggie_program.service.DishFlavorService;
import com.tutsnnd.reggie_program.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;


    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            if (flavor.getDishId()==null){
                flavor.setDishId(id);
            }
        }
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(lambdaQueryWrapper);
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public void saveWithFlaor(DishDto dishDto) {
        this.save(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }
}
