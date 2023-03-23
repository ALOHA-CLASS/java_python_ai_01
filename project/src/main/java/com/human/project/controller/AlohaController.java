package com.human.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j;

@Slf4j
@Controller
@RequestMapping("/aloha")
public class AlohaController {

    @GetMapping("/{sub}")
    public String aloha(@PathVariable("sub") String sub) {

        return "/aloha/" + sub;
    }
    
}
