package com.example.userms.Controller;

import com.example.userms.Service.PrayerTimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PrayerTimesController {
   @Autowired
    private  PrayerTimesService prayerTimesService;

    @GetMapping("/prayer-times")
    public String getPrayerTimes(@RequestParam String city) {
        city= "casablanca" ;
        return prayerTimesService.getPrayerTimes(city);
    }
   // http://localhost:8080/prayer-times?city=london
   @GetMapping("/send-sms")
   public String sendSms() {
       return prayerTimesService.sendSms();
   }
    @GetMapping("/geocode")
    public String getGeocode(@RequestParam(value = "lat", required = false) String lat,
                             @RequestParam(value = "lng", required = false) String lng,
                             Model model) {
        // Exemple de données statiques (remplacez par l'appel à l'API réelle)
        String address = "casablanca maroc rue 7 num 6"; // Simulez une réponse d'API
        model.addAttribute("latitude", lat);
        model.addAttribute("longitude", lng);
        model.addAttribute("address", address);

        return "geocode"; // Retourne la vue Thymeleaf
    }


    @GetMapping("/quran")
    public String getQuranVerses(Model model) {
        String url = "https://al-quran1.p.rapidapi.com/63/1-4";
        String apiKey = "10ce01fc04msha755eb2788ad84dp13d223jsneefd3a84cf7f";

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Ajouter les headers nécessaires
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.add("x-rapidapi-host", "al-quran1.p.rapidapi.com");
            headers.add("x-rapidapi-key", apiKey);

            // Créer une requête HTTP
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            org.springframework.http.ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    String.class
            );

            // Ajouter le résultat à la vue
            model.addAttribute("response", response.getBody());
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur s'est produite : " + e.getMessage());
        }
        return "quran.html";
    }
}
