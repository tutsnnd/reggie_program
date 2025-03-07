package com.tutsnnd.reggie_program.Dto;


import com.tutsnnd.reggie_program.pojo.Setmeal;
import com.tutsnnd.reggie_program.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
