package ee.katrina.videorental.service;

import ee.katrina.videorental.dto.MovieDTO;
import ee.katrina.videorental.entity.Movie;
import ee.katrina.videorental.entity.RentalTransaction;
import ee.katrina.videorental.entity.RentalTransactionLine;
import ee.katrina.videorental.mappers.MovieMapper;
import ee.katrina.videorental.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieMapper movieMapper;

    @Override
    public List<MovieDTO> listAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDTO> getMovieById(UUID movieId) {
        return Optional.ofNullable(movieMapper.movieToMovieDto(movieRepository.findById(movieId).orElse(null)));
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDto) {
        return movieMapper.movieToMovieDto(movieRepository.save(movieMapper.movieDtoToMovie(movieDto)));
    }

    @Override
    public boolean deleteMovieById(UUID movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MovieDTO> updateMovieById(UUID movieId, MovieDTO movieDto) {
        if (movieRepository.findById(movieId).isEmpty()) {
            return Optional.empty();
        }
        Optional<Movie> foundMovie = movieRepository.findById(movieId);
        movieDto.setId(foundMovie.get().getId());
        return Optional.ofNullable(movieMapper.movieToMovieDto(movieRepository.save(
                movieMapper.movieDtoToMovie(movieDto))));
    }

    @Override
    public void decreaseQuantity(RentalTransaction rentalTransaction) {
        for (RentalTransactionLine line : rentalTransaction.getRentalTransactionLines()) {
            Optional<Movie> foundMovie = movieRepository.findById(line.getMovie().getId());
            if (foundMovie.isEmpty()) {
                throw new RuntimeException("Movie not found");
            }
            Integer notRentedOutQuantity = foundMovie.get().getNotRentedOutQuantity();
            if (notRentedOutQuantity < line.getQuantity()) {
                throw new RuntimeException("Not enough copies of movie to rent out");
            }
            foundMovie.get().setNotRentedOutQuantity(notRentedOutQuantity - line.getQuantity());
        }
    }

    @Override
    public List<MovieDTO> getAllAvailableMovies() {
        return movieRepository.findAllByNotRentedOutQuantityIsGreaterThan(0).stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }
}
