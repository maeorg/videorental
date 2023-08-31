package ee.katrina.videorental.controller;

import ee.katrina.videorental.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("movies")
    public ResponseEntity getMovies() {
        return new ResponseEntity(movieRepository.findAll(), HttpStatus.OK);
    }
}
