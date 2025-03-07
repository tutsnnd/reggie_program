package com.tutsnnd.reggie_program.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tutsnnd.reggie_program.Dto.DishDto;
import com.tutsnnd.reggie_program.pojo.Dish;

public interface DishService extends IService<Dish> {
    void updateWithFlavor(DishDto dishDto);

    void saveWithFlaor(DishDto dishDto);
}
