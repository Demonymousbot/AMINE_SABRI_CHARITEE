package com.example.userms.Service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class PrayerTimesService {
    private WebClient webClient;
    public PrayerTimesService(){
        this.webClient = WebClient.builder()
                .baseUrl("https://muslimsalat.p.rapidapi.com")
                .defaultHeader("x-rapidapi-host", "muslimsalat.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "10ce01fc04msha755eb2788ad84dp13d223jsneefd3a84cf7f") // Remplacez avec votre clé
                .build();

    }
    public String getPrayerTimes(String city) {
        return webClient.get()
                .uri("/{city}.json", city) // Remplace "london" par une ville dynamique
                .retrieve()
                .bodyToMono(String.class) // Récupère la réponse sous forme de chaîne JSON brute
                .block();
    }
    // Send an SMS using the Twilio service and BulkSMS API
    public String sendSms() {
        String url = "https://bulksmssender.p.rapidapi.com/";

        // Request body with message and recipient number
        Map<String, Object> requestBody = Map.of(
                "message", "Hello, this is a test SMS!",
                "numbers", new String[]{"+212613192620"} // Example number, use actual
        );

        // Make the POST request to send the SMS
        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .header("X-Twilio-AccountSid", "ACf905a018b5823534d2c134777c26fe40") // Replace with your actual Twilio SID
                .header("X-Twilio-AuthToken", "7743ecfb4f03aa0a2a363bc5eb8245d1") // Replace with your Twilio Auth Token
                .header("X-Twilio-Number", "17754597187") // Replace with your Twilio phone number
                .header("x-rapidapi-host", "bulksmssender.p.rapidapi.com")
                .header("x-rapidapi-key", "10ce01fc04msha755eb2788ad84dp13d223jsneefd3a84cf7f") // Replace with your actual RapidAPI key
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getAddress(String lat, String lng) {
        String url = "https://google-maps-geocoding.p.rapidapi.com/geocode/json?latlng=" + lat + "," + lng;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        // Parsez la réponse JSON pour obtenir l'adresse
        return "Adresse simulée"; // Remplacez par une valeur réelle
    }

}
