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

    @Value("${rapidapi.instagram120.url}")
    private String instagramUrl;

    @Value("${rapidapi.instagram120.host}")
    private String instagramHost;

    @Value("${rapidapi.instagram120.apikey}")
    private String instagramApikey;

    @Bean(name = "youtubeWebClient")
    public WebClient youtubeWebClient() {
        return WebClient.builder()
                .baseUrl(rapidapiUrl)
                .defaultHeader("x-rapidapi-host", rapidapiHost)
                .defaultHeader("x-rapidapi-key", rapidapiApikey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean(name = "instagramWebClient")
    public WebClient instagramWebClient() {
        return WebClient.builder()
                .baseUrl(instagramUrl)
                .defaultHeader("x-rapidapi-host", instagramHost)
                .defaultHeader("x-rapidapi-key", instagramApikey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // Mantener el bean principal para compatibilidad
    @Bean
    public WebClient webClient() {
        return youtubeWebClient();
    }
}