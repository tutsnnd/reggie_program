package com.tutsnnd.reggie_program.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tutsnnd.reggie_program.mapper.EmployeeMapper;
import com.tutsnnd.reggie_program.pojo.Employee;
import com.tutsnnd.reggie_program.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
