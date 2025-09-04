package pe.edu.vallegrande.ApiAI.service;

import pe.edu.vallegrande.ApiAI.model.Instagram;
import reactor.core.publisher.Mono;

public interface InstagramService {
    
    Mono<Instagram> getInstagramProfile(String username);
}