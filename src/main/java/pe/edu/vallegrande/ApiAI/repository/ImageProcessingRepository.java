package pe.edu.vallegrande.ApiAI.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.ApiAI.model.ImageProcessing;
import reactor.core.publisher.Mono;

@Repository
public interface ImageProcessingRepository extends ReactiveCrudRepository<ImageProcessing, Long> {
    
   Mono<ImageProcessing> findById(Long id);

}