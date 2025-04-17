package com.example.bakerybe.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class KeepAliveService {

    @Scheduled(fixedRate = 300000)
    public void pingSelf() {
        try {
            URL url = new URL("https://bakery-be.onrender.com/internal/keepalive");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.getResponseCode();
            System.out.println("Keep-alive ping sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
