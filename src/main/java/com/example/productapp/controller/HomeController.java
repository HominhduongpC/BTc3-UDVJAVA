package com.example.productapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Vào trang gốc "/" thì tự chuyển sang danh sách sản phẩm
    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }
}
