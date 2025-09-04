package pe.edu.vallegrande.ApiAI.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import pe.edu.vallegrande.ApiAI.model.YoutubeMP3;
import pe.edu.vallegrande.ApiAI.service.YoutubeMP3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/youtube")
@Tag(name = "YouTube MP3 API", description = "API para convertir videos de YouTube a MP3")
public class YoutubeMP3Rest {

    private final YoutubeMP3Service youtubeMP3Service;

    @Autowired
    public YoutubeMP3Rest(YoutubeMP3Service youtubeMP3Service) {
        this.youtubeMP3Service = youtubeMP3Service;
    }

    @GetMapping("/{videoId}")
    @Operation(
        summary = "Convertir video de YouTube a MP3",
        description = "Obtiene el enlace de descarga MP3 de un video de YouTube usando su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Enlace de descarga obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = YoutubeMP3.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "ID de video inválido"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
        )
    })
    public Mono<YoutubeMP3> getVideo(
            @Parameter(description = "ID del video de YouTube", required = true, example = "dQw4w9WgXcQ")
            @PathVariable String videoId) {
        return youtubeMP3Service.fetchDownloadUrl(videoId); 
    }
}