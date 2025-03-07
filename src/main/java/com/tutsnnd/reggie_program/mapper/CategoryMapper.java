package com.tutsnnd.reggie_program.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutsnnd.reggie_program.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
