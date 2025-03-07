package com.tutsnnd.reggie_program.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutsnnd.reggie_program.Dto.DishDto;
import com.tutsnnd.reggie_program.Dto.SetmealDto;
import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.Category;
import com.tutsnnd.reggie_program.pojo.Dish;
import com.tutsnnd.reggie_program.pojo.DishFlavor;
import com.tutsnnd.reggie_program.service.CategoryService;
import com.tutsnnd.reggie_program.service.DishFlavorService;
import com.tutsnnd.reggie_program.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;


    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page pageDto = new Page();
        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<Dish>()
                .like(name!=null,Dish::getName,name)
                .orderByAsc(Dish::getUpdateTime);
        dishService.page(pageInfo,lambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo,pageDto,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> recordsDto = new ArrayList<>();
        for (Dish record : records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(record,dishDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                dishDto.setCategoryName(category.getName());
            }
            recordsDto.add(dishDto);

        }
        pageDto.setRecords(recordsDto);


        return R.success(pageDto);
    }

    @DeleteMapping()
    public R<String> delete (@RequestParam ArrayList<Long> ids){
//        log.info("{},{}",ids.get(0),ids.get(1));
        dishService.removeByIds(ids);
        return R.success("删除成功");
    }

    @GetMapping("{id}")
    public R<DishDto> getById (@PathVariable Long id){
        DishDto dishDto= new DishDto();
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
        Dish dish = dishService.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        Category category = categoryService.getById(dish.getCategoryId());
        dishDto.setCategoryName(category.getName());
        dishDto.setFlavors(dishFlavors);
        return R.success(dishDto);
    }

    @PostMapping("/status/{type}")
    public  R<String> statusChange(@PathVariable int type,@RequestParam ArrayList<Long> ids){

//        List<Dish> dishes = dishService.listByIds(ids);
//        for (int i = 0; i < dishes.size(); i++) {
//            dishes.get(i).setStatus(type);
//        }
//        dishService.updateBatchById(dishes);
        UpdateWrapper<Dish> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",ids).set("status",type);
        dishService.update(updateWrapper);
        return R.success("修改成功");
    }

    @PutMapping
    public R<String> update (@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        dishService.saveWithFlaor(dishDto);
        return R.success("保存成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> list (@RequestParam Long categoryId){

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<Dish>()
                .eq(Dish::getCategoryId,categoryId);
        List<Dish> list = dishService.list(lambdaQueryWrapper);
        return R.success(list);
    }

}
