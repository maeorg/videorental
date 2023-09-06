package ee.katrina.videorental.mappers;

import ee.katrina.videorental.dto.MovieDTO;
import ee.katrina.videorental.entity.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface MovieMapper {

    Movie movieDtoToMovie(MovieDTO movieDTO);

    MovieDTO movieToMovieDto(Movie movie);
}
