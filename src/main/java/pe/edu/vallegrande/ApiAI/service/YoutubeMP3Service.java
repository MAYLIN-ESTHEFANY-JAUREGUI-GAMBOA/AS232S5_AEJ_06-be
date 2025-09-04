package pe.edu.vallegrande.ApiAI.service;

import pe.edu.vallegrande.ApiAI.model.YoutubeMP3;
import reactor.core.publisher.Mono;

public interface YoutubeMP3Service {

    Mono<YoutubeMP3> fetchDownloadUrl(String videoId);

}
