package com.tutsnnd.reggie_program.controller;

import com.tutsnnd.reggie_program.common.R;
import com.tutsnnd.reggie_program.pojo.ShoppingCart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @PostMapping("/list")
    public R<List<ShoppingCart>> list(){

        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        return R.success(shoppingCarts);
    }
}
