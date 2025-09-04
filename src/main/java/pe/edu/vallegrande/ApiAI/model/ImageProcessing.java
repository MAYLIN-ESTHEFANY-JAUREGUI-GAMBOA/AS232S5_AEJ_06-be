package pe.edu.vallegrande.ApiAI.model;

import lombok.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "imageprocessing_profile")
public class ImageProcessing {
    @Id
    private Long id;

    @Column(value = "text")
    private String text;
                            
    @Column(value = "width")
    private String width;

    @Column(value = "height")
    private String height;

    @Column(value = "generated_image")
    private String generated_image;

    @Column(value = "creation_date")
    private LocalDateTime creationDate;

    @Column(value = "update_date")
    private LocalDateTime updateDate;
}