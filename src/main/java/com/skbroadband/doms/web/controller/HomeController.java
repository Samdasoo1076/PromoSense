package com.skbroadband.doms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home/index")
    public String homeIndex() {
        // templates/home/index.html 파일을 렌더링해줌
        return "home/index";
    }
}
