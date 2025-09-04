package pe.edu.vallegrande.ApiAI.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.ApiAI.model.ImageProcessing;
import pe.edu.vallegrande.ApiAI.service.ImageProcessingService;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/image")
@CrossOrigin(origins = "*")
@Tag(name = "Image Processing API", description = "API para generar y obtener imágenes procesadas con texto y dimensiones")
public class ImageProcessingRest {

    private final ImageProcessingService imageService;

    @Autowired
    public ImageProcessingRest(ImageProcessingService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/generate")
    @Operation(
        summary = "Generar imagen",
        description = "Genera una imagen a partir de un texto, ancho y alto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Imagen generada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImageProcessing.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o incompletos"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public Mono<ResponseEntity<ImageProcessing>> generateImage(
            @Parameter(description = "Objeto JSON con text, width, height", required = true)
            @RequestBody Map<String, String> request) {

        String text = request.get("text");
        String width = request.get("width");
        String height = request.get("height");

        if (text == null || text.trim().isEmpty() || width == null || height == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return imageService.generateImage(text.trim(), width.trim(), height.trim())
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/get/{id}")
    @Operation(
        summary = "Obtener imagen generada",
        description = "Devuelve la información de una imagen generada por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Imagen obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ImageProcessing.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Imagen no encontrada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public Mono<ResponseEntity<ImageProcessing>> getImageById(
            @Parameter(description = "ID de la imagen generada", required = true, example = "1")
            @PathVariable Long id) {

        return imageService.getImageById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
