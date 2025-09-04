package pe.edu.vallegrande.ApiAI.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.ApiAI.model.ImageProcessing;
import pe.edu.vallegrande.ApiAI.repository.ImageProcessingRepository;
import pe.edu.vallegrande.ApiAI.service.ImageProcessingService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private final ImageProcessingRepository imageProcessingRepository;
    private final WebClient webClient;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Autowired
    public ImageProcessingServiceImpl(
            ImageProcessingRepository imageProcessingRepository,
            @Qualifier("webClient") WebClient webClient) {
        this.imageProcessingRepository = imageProcessingRepository;
        this.webClient = webClient;
    }

    @Override
    public Mono<ImageProcessing> generateImage(String text, String width, String height) {
        return fetchFromRapidApi(text, width, height)
                .flatMap(response -> {
                    ImageProcessing imageProcessing = mapToImageProcessing(response, text, width, height);
                    return imageProcessingRepository.save(imageProcessing);
                })
                .doOnSuccess(saved -> System.out.println("Successfully saved image with id: " + saved.getId()))
                .doOnError(error -> System.err.println("Error in generateImage: " + error.getMessage()));
    }

    @Override
    public Mono<ImageProcessing> getImageById(Long id) {
        return imageProcessingRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Image not found with id: " + id)));
    }

    private Mono<JsonNode> fetchFromRapidApi(String text, String width, String height) {
        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("text", text);
        requestBody.put("width", Integer.parseInt(width));
        requestBody.put("height", Integer.parseInt(height));

        return webClient.post()
                .uri("https://chatgpt-42.p.rapidapi.com/texttoimage")
                .header("X-RapidAPI-Key", rapidApiKey)
                .header("X-RapidAPI-Host", "chatgpt-42.p.rapidapi.com")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnNext(response -> System.out.println("API Response: " + response))
                .doOnError(error -> System.err.println("Error calling RapidAPI: " + error.getMessage()));
    }

    private ImageProcessing mapToImageProcessing(JsonNode jsonNode, String text, String width, String height) {
        ImageProcessing imageProcessing = new ImageProcessing();
        imageProcessing.setText(text);
        imageProcessing.setWidth(width);
        imageProcessing.setHeight(height);
        
        // Assuming the API returns the image URL in a 'url' field
        // Adjust this based on the actual API response structure
        if (jsonNode.has("url")) {
            imageProcessing.setGenerated_image(jsonNode.get("url").asText());
        } else if (jsonNode.has("generated_image")) {
            imageProcessing.setGenerated_image(jsonNode.get("generated_image").asText());
        } else {
            // If the response doesn't contain the expected fields, store the raw response
            imageProcessing.setGenerated_image(jsonNode.toString());
        }

        LocalDateTime now = LocalDateTime.now();
        imageProcessing.setCreationDate(now);
        imageProcessing.setUpdateDate(now);

        return imageProcessing;
    }
}
