package com.tutsnnd.reggie_program.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tutsnnd.reggie_program.Dto.SetmealDto;
import com.tutsnnd.reggie_program.pojo.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void updateWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
