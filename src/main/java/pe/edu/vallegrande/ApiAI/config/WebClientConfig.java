package pe.edu.vallegrande.ApiAI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${rapidapi.youtube-mp36.url}")
    private String rapidapiUrl;

    @Value("${rapidapi.youtube-mp36.host}")
    private String rapidapiHost;

    @Value("${rapidapi.youtube-mp36.apikey}")
    private String rapidapiApikey;

    @Value("${imageprocessing.url}")
    private String imageprocessingUrl;

    @Value("${imageprocessing.host}")
    private String imageprocessingHost;

    @Value("${imageprocessing.apikey}")
    private String imageprocessingApiKey;

    @Bean(name = "youtubeWebClient")
    public WebClient youtubeWebClient() {
        return WebClient.builder()
                .baseUrl(rapidapiUrl)
                .defaultHeader("x-rapidapi-host", rapidapiHost)
                .defaultHeader("x-rapidapi-key", rapidapiApikey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean(name = "imageProcessingWebClient")
    public WebClient imageProcessingWebClient() {
    return WebClient.builder()
                .baseUrl(imageprocessingUrl)
                .defaultHeader("x-rapidapi-host", imageprocessingHost)
                .defaultHeader("x-rapidapi-key", imageprocessingApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // Mantener el bean principal para compatibilidad
    @Bean
    public WebClient webClient() {
        return youtubeWebClient();
    }
}