package ee.katrina.videorental.service;

import ee.katrina.videorental.dto.MovieDTO;
import ee.katrina.videorental.entity.Movie;
import ee.katrina.videorental.entity.RentalTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {


    List<MovieDTO> listAllMovies();

    Optional<MovieDTO> getMovieById(UUID movieId);

    MovieDTO addMovie(MovieDTO movieDto);

    boolean deleteMovieById(UUID movieId);

    Optional<MovieDTO> updateMovieById(UUID movieId, MovieDTO movieDto);

    List<MovieDTO> getAllAvailableMovies();
}
