package ee.katrina.videorental.entity;

import ee.katrina.videorental.model.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    private Integer releaseYear;
    @NotNull
    private Integer lengthInMinutes;
    private List<Genre> genres;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Director> directors;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Actor> stars;

    @ManyToMany(cascade = {jakarta.persistence.CascadeType.ALL})
    private List<Writer> writers;

}
