package com.human.project.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "charts")
public class Chart {

    @Id
    private String id;          // 식별용 id
    private String name;        // 곡 이름
    private String artist;      // 가수
    private byte[] img;         // 엘범 이미지
    private String videoUrl;    // yotube URL

    public Chart(String name, String artist, byte[] img, String videoUrl) {
        this.name = name;
        this.artist = artist;
        this.img = img;
        this.videoUrl = videoUrl;
        // 수정
    }
}

