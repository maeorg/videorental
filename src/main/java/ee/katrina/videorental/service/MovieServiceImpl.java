package ee.katrina.videorental.service;

import ee.katrina.videorental.entity.Movie;
import ee.katrina.videorental.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> listAllMovies() {
        return movieRepository.findAll();
    }
}
