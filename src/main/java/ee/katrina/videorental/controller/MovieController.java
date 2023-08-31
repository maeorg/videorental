package ee.katrina.videorental.controller;

import ee.katrina.videorental.entity.Movie;
import ee.katrina.videorental.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("movies")
    public ResponseEntity listAllMovies() {
        List<Movie> movies = movieService.listAllMovies();
        return new ResponseEntity(movies, HttpStatus.OK);
    }
}
