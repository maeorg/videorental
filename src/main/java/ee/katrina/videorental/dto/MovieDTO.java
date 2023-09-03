package ee.katrina.videorental.dto;

import ee.katrina.videorental.entity.Actor;
import ee.katrina.videorental.entity.Director;
import ee.katrina.videorental.entity.Writer;
import ee.katrina.videorental.model.Genre;
import ee.katrina.videorental.model.MovieType;
import jakarta.persistence.ManyToMany;
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
    private MovieType movieType;
    private Integer totalQuantity;
    private Integer notRentedOutQuantity;
    private List<Genre> genres;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Director> directors;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Actor> stars;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Writer> writers;

}
