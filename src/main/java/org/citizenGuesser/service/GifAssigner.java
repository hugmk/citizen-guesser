package org.citizenGuesser.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class GifAssigner {

    @Value("${api.giphy.url}")
    private String giphy;

    public String getGifUrl(int score) {
        String gifUrl;

        if(score < 5) {
            gifUrl = giphy + "&q=cat+fail";
        }
        else if(score < 15) {
            gifUrl = giphy + "&q=not+bad";
        }
        else if(score < 30) {
            gifUrl = giphy + "&q=nice";
        }
        else if(score < 50) {
            gifUrl = giphy + "&q=awesome";
        }
        else if(score < 100) {
            gifUrl = giphy + "&q=applause";
        }
        else {
            gifUrl = giphy + "&q=goat";
        }

        return getGif(gifUrl);
    }

    public String getGif(String gifUrl) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(gifUrl, String.class);

        JSONObject jsonResponse = new JSONObject(response);

        Random rand = new Random();
        int randomIndex = rand.nextInt(jsonResponse.getJSONArray("data").length());

        String imageUrl = jsonResponse.getJSONArray("data")
                .getJSONObject(randomIndex)
                .getJSONObject("images")
                .getJSONObject("downsized")
                .getString("url");

        return imageUrl;
    }

}
