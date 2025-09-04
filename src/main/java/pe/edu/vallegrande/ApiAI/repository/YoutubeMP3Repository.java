package pe.edu.vallegrande.ApiAI.repository;

import pe.edu.vallegrande.ApiAI.model.YoutubeMP3;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeMP3Repository extends ReactiveCrudRepository<YoutubeMP3, Long> {
    
}
