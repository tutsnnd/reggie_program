package com.tutsnnd.reggie_program.Dto;

import com.tutsnnd.reggie_program.pojo.Dish;
import com.tutsnnd.reggie_program.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
