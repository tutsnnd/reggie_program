package com.tutsnnd.reggie_program.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.User;
import com.tutsnnd.reggie_program.service.UserService;
import com.tutsnnd.reggie_program.utlis.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg (@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            session.setAttribute(phone,code);
            log.info("验证码为：{}",code);
            return R.success("发送成功！");
        }

        return R.error("未知错误");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map ,HttpSession session){

        String  phone = (String) map.get("phone");
        String code = map.get("code").toString();
        if (session.getAttribute(phone).equals(code)){
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
                    .eq(User::getPhone,phone);
            User user = userService.getOne(lambdaQueryWrapper);
            if (user==null){
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
                return R.success(user);
        }
        return R.error("未知错误");
    }
}
