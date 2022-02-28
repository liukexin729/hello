package com.example.hello.controller;

import com.example.hello.server.impl.CommandServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class CommandController {

    @Autowired
    private CommandServiceImpl commandService;

    @PostMapping(value = "/execute")
    public String executeCmd(String cmd) {

        //cmd chmod 755 /home/lkx/123

         log.info("params:{}",cmd);
         return commandService.execute(cmd);
    }
}
