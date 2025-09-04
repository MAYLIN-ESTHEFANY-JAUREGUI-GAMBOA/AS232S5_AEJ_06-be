package pe.edu.vallegrande.ApiAI.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.ApiAI.model.ImageProcessing;
import pe.edu.vallegrande.ApiAI.repository.ImageProcessingRepository;
import pe.edu.vallegrande.ApiAI.service.ImageProcessingService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private final ImageProcessingRepository imageProcessingRepository;
    private final WebClient imageProcessingWebClient;

    @Autowired
    public ImageProcessingServiceImpl(
            ImageProcessingRepository imageProcessingRepository,
            @Qualifier("imageProcessingWebClient") WebClient imageProcessingWebClient) {
        this.imageProcessingRepository = imageProcessingRepository;
        this.imageProcessingWebClient = imageProcessingWebClient;
    }

    @Override
    public Mono<ImageProcessing> generateImage(String text, String width, String height) {
        return fetchFromApi(text, width, height)
                .map(jsonNode -> mapToImageProcessing(jsonNode, text, width, height))
                .flatMap(imageProcessingRepository::save)
                .doOnError(error -> System.err.println("Error generating image: " + error.getMessage()));
    }

    @Override
    public Mono<ImageProcessing> getImageById(Long id) {
        return imageProcessingRepository.findById(id);
    }

    private Mono<JsonNode> fetchFromApi(String text, String width, String height) {
        return imageProcessingWebClient.post()
                .uri("/v1/api/image") // 👈 Aquí asegúrate de que sea el endpoint correcto
                .bodyValue(Map.of(
                        "text", text,
                        "width", width,
                        "height", height,
                        "steps", 1
                ))
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    private ImageProcessing mapToImageProcessing(JsonNode jsonNode, String text, String width, String height) {
        ImageProcessing imageProcessing = new ImageProcessing();

        imageProcessing.setText(text);
        imageProcessing.setWidth(width);
        imageProcessing.setHeight(height);

        if (jsonNode.has("generated_image")) {
            imageProcessing.setGenerated_image(jsonNode.get("generated_image").asText());
        }

        LocalDateTime now = LocalDateTime.now();
        imageProcessing.setCreationDate(now);
        imageProcessing.setUpdateDate(now);

        return imageProcessing;
    }
}
