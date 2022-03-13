package com.example.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/hello")
    public String hello(String str) {

        LOGGER.info("接收的参数:{}",str);

        return str+" "+"World!";
    }

    @RequestMapping(value = "/hello1")
    public String hello1(String str) {

        LOGGER.info("接收的参数:{}",str);

        return str+" "+"World!";
    }
}
