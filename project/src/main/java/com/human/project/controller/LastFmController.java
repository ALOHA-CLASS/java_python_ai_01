package com.human.project.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.human.project.domain.Chart;
import com.human.project.mapper.ChartRepository;
import com.human.project.service.LastFmService;

@Controller
public class LastFmController {

    @Autowired
    private ChartRepository chartRepository;

    private final LastFmService lastFmService;

    public LastFmController(LastFmService lastFmService) {
        this.lastFmService = lastFmService;
    }

    @GetMapping("/chart_reset")
    public String Reset() throws IOException {
        chartRepository.deleteAll();
        lastFmService.getChart();
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> showImage(@PathVariable String id) {
        Optional<Chart> image = chartRepository.findById(id);
        if (image.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(image.get().getImg(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
