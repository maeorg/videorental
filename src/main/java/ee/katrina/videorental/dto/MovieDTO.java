package ee.katrina.videorental.dto;

import ee.katrina.videorental.model.Genre;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class MovieDTO {
    private UUID id;
    private String title;
    private Integer releaseYear;
    private Integer lengthInMinutes;
    private List<Genre> genres;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
