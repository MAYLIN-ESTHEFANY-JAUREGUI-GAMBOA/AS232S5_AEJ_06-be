package pe.edu.vallegrande.ApiAI.service;

import pe.edu.vallegrande.ApiAI.model.ImageProcessing;
import reactor.core.publisher.Mono;

public interface ImageProcessingService {

    // Generar imagen a partir de parámetros
    Mono<ImageProcessing> generateImage(String text, String width, String height);

    // Obtener imagen generada por ID
    Mono<ImageProcessing> getImageById(Long id);
}
