package com.example.hello.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping(value = "/test")
    public String test() {
        System.out.println("test");
        return "success";
    }

    @PostMapping(value = "/test4")
    public String test4() {
        System.out.println("test");
        return "success";
    }

}
