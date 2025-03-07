package com.tutsnnd.reggie_program.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutsnnd.reggie_program.common.BaseContext;
import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.Employee;
import com.tutsnnd.reggie_program.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping( "/login")
    public R<Employee> login(@RequestBody Employee employee,HttpServletRequest request){
        LambdaQueryWrapper<Employee> lambdaQueryWrapper= new LambdaQueryWrapper<Employee>()
                .eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(lambdaQueryWrapper);
        String password=employee.getPassword();
        if(emp==null){
            return R.error("账号不存在");
        }
        if(!password.equals(emp.getPassword())){
            return R.error("密码错误");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);


    }
    @GetMapping("/page")
    @Cacheable(value = "employeeCache",key = "#page+'_'+#pageSize+'_'+#name")
    public R<Page> page(int page, int pageSize, String name){

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .like(name!=null,Employee::getName,name);
        employeeService.page(pageInfo,lambdaQueryWrapper);

        return R.success(pageInfo);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> save(@RequestBody Employee employee){
        employee.setPassword("123456");
        log.info("{}",employee.getCreateTime());
        employeeService.save(employee);
        log.info("{}",employee.getCreateTime());
        return R.success("添加成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);

    }
    @PutMapping
    public R<String> update (@RequestBody Employee employee){

        employeeService.updateById(employee);

        return R.success("修改成功！");
    }
}
