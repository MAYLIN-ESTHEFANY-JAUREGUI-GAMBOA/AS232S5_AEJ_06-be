package pe.edu.vallegrande.ApiAI.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.ApiAI.model.Instagram;
import reactor.core.publisher.Mono;

@Repository
public interface InstagramRepository extends ReactiveCrudRepository<Instagram, Long> {
    
    Mono<Instagram> findByUsername(String username);
}