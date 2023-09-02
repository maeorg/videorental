package ee.katrina.videorental.controller;

import ee.katrina.videorental.dto.MovieDTO;
import ee.katrina.videorental.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class MovieController {

    public static final String MOVIE_PATH = "/api/v1/movie";
    public static final String MOVIE_PATH_ID = MOVIE_PATH + "/{movieId}";

    @Autowired
    MovieService movieService;

    // get all available (not rented out) movies
    @GetMapping(value = MOVIE_PATH + "/available")
    public ResponseEntity getAllAvailableMovies() {
        List<MovieDTO> movieList = movieService.getAllAvailableMovies();
        return new ResponseEntity(movieList, HttpStatus.OK);
    }

    // get all movies
    @GetMapping(value = MOVIE_PATH)
    public ResponseEntity getAllMovies() {
        List<MovieDTO> movies = movieService.listAllMovies();
        return new ResponseEntity(movies, HttpStatus.OK);
    }

    // get movie by id
    @GetMapping(value = MOVIE_PATH_ID)
    public ResponseEntity getMovieById(@PathVariable UUID movieId) {
        MovieDTO movieDTO = movieService.getMovieById(movieId).orElseThrow(RuntimeException::new);
        return new ResponseEntity(movieDTO, HttpStatus.OK);
    }

    // add movie
    @PostMapping(value = MOVIE_PATH)
    public ResponseEntity addMovie(@RequestBody MovieDTO movieDto) {
        MovieDTO savedMovieDTO = movieService.addMovie(movieDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", MOVIE_PATH + "/" + savedMovieDTO.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    // remove movie
    @DeleteMapping(value = MOVIE_PATH_ID)
    public ResponseEntity deleteMovieById(@PathVariable UUID movieId) {
        if (!movieService.deleteMovieById(movieId)) {
            throw new RuntimeException("Movie not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // update movie
    @PutMapping(MOVIE_PATH_ID)
    public ResponseEntity updateMovieById(@PathVariable UUID movieId, @RequestBody MovieDTO movieDto) {
        if (movieService.updateMovieById(movieId, movieDto).isEmpty()) {
            throw new RuntimeException("Movie not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
