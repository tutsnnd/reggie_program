package com.tutsnnd.reggie_program.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutsnnd.reggie_program.Dto.SetmealDto;
import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.Category;
import com.tutsnnd.reggie_program.pojo.Dish;
import com.tutsnnd.reggie_program.pojo.Setmeal;
import com.tutsnnd.reggie_program.pojo.SetmealDish;
import com.tutsnnd.reggie_program.service.CategoryService;
import com.tutsnnd.reggie_program.service.DishService;
import com.tutsnnd.reggie_program.service.SetmealDishService;
import com.tutsnnd.reggie_program.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Transactional
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @Cacheable(value = "setmealCache",key = "#page+'_'+#pageSize+'_'+#name")
    public R<Page> page (int page,int pageSize,String name){
        Page pageDto = new Page();
        Page pageinfo = new Page(page,pageSize);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<Setmeal>()
                .like(name!=null,Setmeal::getName,name);

        setmealService.page(pageinfo,lambdaQueryWrapper);
        BeanUtils.copyProperties(pageinfo,pageDto,"records");
        List<Setmeal> records = pageinfo.getRecords();
        List<SetmealDto> list = new ArrayList<>();
        for (Setmeal record : records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record,setmealDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmealDto.setCategoryName(category.getName());
            list.add(setmealDto);
        }
        pageDto.setRecords(list);
        return R.success(pageDto);

    }

    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save (@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById (@PathVariable Long id){

        Setmeal byId = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(byId,setmealDto);
        Category category = categoryService.getById(byId.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<SetmealDish>()
                .eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealList = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(setmealList);
        return R.success(setmealDto);
    }
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> update (@RequestBody SetmealDto setmealDto){

        setmealService.updateWithDish(setmealDto);
        return R.success("修改成功！");
    }

    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete (@RequestParam List<Long> ids){

        setmealService.removeWithDish(ids);
        log.info("热部署测试");
        return R.success("删除成功！");
    }

    @PostMapping("/status/{type}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> statusChange(@PathVariable int type, @RequestParam ArrayList<Long> ids){

        LambdaUpdateWrapper<Setmeal> lambdaUpdateWrapper = new LambdaUpdateWrapper<Setmeal>()
                .set(Setmeal::getStatus,type)
                .in(Setmeal::getId,ids);

        setmealService.update(lambdaUpdateWrapper);
        return R.success("修改成功");
    }
 }
