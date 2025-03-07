package com.tutsnnd.reggie_program.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.Category;
import com.tutsnnd.reggie_program.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@Slf4j
@RestController
public class CategoryController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page (int page,int pageSize){

        Page pageInfo = new Page(page,pageSize);
        categoryService.page(pageInfo);
        return R.success(pageInfo);
    }

    @CachePut(value = "category",key ="#category.id")
    @PostMapping
    public R<String> save (@RequestBody Category category){

        categoryService.save(category);
        return R.success("添加成功");
    }

    @DeleteMapping
    public R<String> delete (Long ids){

        categoryService.removeById(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Category>> list (@RequestParam int type){

        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getType,type);
        List<Category> categoryList = categoryService.list(lambdaQueryWrapper);

        return R.success(categoryList);
    }
}
