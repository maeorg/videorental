package ee.katrina.videorental.entity;

import ee.katrina.videorental.model.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    private String title;
    private Integer releaseYear;
    private Integer lengthInMinutes;
    private List<Genre> genres;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @ManyToMany(mappedBy = "movies")
    private List<Director> directors;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> stars;

    @ManyToMany(mappedBy = "movies")
    private List<Writer> writers;

}
