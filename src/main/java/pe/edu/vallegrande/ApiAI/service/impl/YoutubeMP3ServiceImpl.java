package pe.edu.vallegrande.ApiAI.service.impl;

import pe.edu.vallegrande.ApiAI.model.YoutubeMP3;
import pe.edu.vallegrande.ApiAI.repository.YoutubeMP3Repository;
import pe.edu.vallegrande.ApiAI.service.YoutubeMP3Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YoutubeMP3ServiceImpl implements YoutubeMP3Service {

    private final YoutubeMP3Repository youtubeMP3Repository;
    private final WebClient ageWebClient;  // Aquí se inyectará el WebClient de YouTube

    LocalDateTime now = LocalDateTime.now();

    @Autowired
    public YoutubeMP3ServiceImpl(YoutubeMP3Repository youtubeMP3Repository, 
                                 @Qualifier("youtubeWebClient") WebClient ageWebClient) {  // Usamos @Qualifier
        this.youtubeMP3Repository = youtubeMP3Repository;
        this.ageWebClient = ageWebClient;
    }

    @Override
    public Mono<YoutubeMP3> fetchDownloadUrl(String videoId) {
        log.info("Obteniendo URL de descarga para el video ID = " + videoId);
        return ageWebClient.get()
                .uri("/dl?id=" + videoId)  // La URL que se usará para la solicitud GET
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    // Extraemos el link de descarga del JSON
                    String downloadUrl = json.replaceAll(".*\"link\":\"([^\"]+)\".*", "$1");
                    return downloadUrl;
                })
                .flatMap(downloadUrl -> {
                    YoutubeMP3 youtubeMP3 = new YoutubeMP3();
                    youtubeMP3.setVideoId(videoId);
                    youtubeMP3.setVideoUrl("https://www.youtube.com/watch?v=" + videoId);
                    youtubeMP3.setDownloadUrl(downloadUrl);
                    youtubeMP3.setCreationDate(now);
                    youtubeMP3.setUpdateDate(now);
                    log.info("Guardando el video con URL de descarga: " + youtubeMP3.toString());
                    return youtubeMP3Repository.save(youtubeMP3);
                });
    }

}
