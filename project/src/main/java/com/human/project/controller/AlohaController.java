package com.human.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.project.domain.Chart;
import com.human.project.mapper.ChartRepository;

import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("/aloha")
public class AlohaController {

    @Autowired
    private ChartRepository chartRepository;


    @GetMapping("/{sub}")
    public String aloha(@PathVariable("sub") String sub, Model model) {
        List<Chart> trackList = chartRepository.findAll();
        model.addAttribute("trackList", trackList);

        return "/aloha/" + sub;
    }

    // 썸네일 보여주기
   @GetMapping("/img/{img}")
   public ResponseEntity<byte[]> showImg(@PathVariable("img") byte[] img) {

       return new ResponseEntity<>(img, HttpStatus.OK);
   }
   
    
}
