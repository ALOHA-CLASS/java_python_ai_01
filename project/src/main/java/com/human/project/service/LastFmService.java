package com.human.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.human.project.domain.Chart;
import com.human.project.mapper.ChartRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
@PropertySource("classpath:application.properties")
public class LastFmService {
    @Autowired
    private ChartRepository chartRepository;

    @Value("${lastfm-api-key}")
    private String lastfm_api_key;

    private static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";

    @Value("${yotube-api-key}")
    private String youtube_api_key;

    public void getChart() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url1 = String.format("%s?method=chart.gettoptracks&api_key=%s&page=1&format=json", BASE_URL, lastfm_api_key);
        Request request = new Request.Builder()
                .url(url1)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode trackNode = rootNode.path("tracks").path("track");

        List<Chart> charts = new ArrayList<>();
        for (JsonNode node : trackNode) {
            String name = node.path("name").asText();
            String artist = node.path("artist").path("name").asText();

            String url_s = "https://www.googleapis.com/youtube/v3/search?key=" + youtube_api_key + "&type=video&regionCode=GB&part=snippet&q=" +artist + name;

            Request request_s = new Request.Builder()
                .url(url_s)
                .build();
            Response response_s = client.newCall(request_s).execute();
            String responsebody_s = response_s.body().string();
    
            mapper = new ObjectMapper();
            JsonNode rootSearch = mapper.readTree(responsebody_s);

            String videoId = "";
            for (int i = 0; i<=3; i++){
                if (rootSearch.path("items").path(i).path("snippet").path("title").asText().contains("(Official Video)") || rootSearch.path("items").path(i).path("snippet").path("title").asText().contains("(Official Lyric Video)")){
                    videoId = rootSearch.path("items").path(i).path("id").path("videoId").asText();
                    break;
                }  else {
                    videoId = rootSearch.path("items").path(0).path("id").path("videoId").asText();
                }
            }
            System.out.println(videoId);
            String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

            request = new Request.Builder()
                .url(BASE_URL + "?method=track.getInfo&api_key=" + lastfm_api_key + "&artist=" + artist + "&track=" + name +  "&format=json")
                .build();
            response = client.newCall(request).execute();
            String responsebody = response.body().string();

            mapper = new ObjectMapper();
            JsonNode rootNode2 = mapper.readTree(responsebody);
            String imageUrl = rootNode2.path("track").path("album").path("image").path(1).path("#text").asText();
           
            if (imageUrl.isEmpty()){
                ClassPathResource resource = new ClassPathResource("/static/default4.png");
                InputStream inputStream = resource.getInputStream();
                byte[] imageData = ByteStreams.toByteArray(inputStream);
                Chart chart = new Chart(name, artist, imageData, videoUrl);
                charts.add(chart);
                chartRepository.save(chart);
            } else {
                byte[] imageData = downloadImage(imageUrl);   
                Chart chart = new Chart(name, artist, imageData, videoUrl);
                charts.add(chart); 
                chartRepository.save(chart);
            }  
        }
        
    }

    private byte[] downloadImage(String imageUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imageUrl).build();

        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();
            byte[] imageData = IOUtils.toByteArray(inputStream);

            File tempFile = File.createTempFile("album", ".png");
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(imageData);
            outputStream.close();

            return imageData;
        }
    }
}

