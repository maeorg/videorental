import { Component } from '@angular/core';
import { Movie } from '../models/movie.model';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent {

  movies: Movie[] = [];

  constructor(private movieService: MovieService) {}

  ngOnInit() {
    this.movieService.getMovies()
    .subscribe((data: Movie[]) => {
      this.movies = data;
    });
  }

  allMovies() {
    this.movieService.getMovies()
    .subscribe((data: Movie[]) => {
      this.movies = data;
    });
  }

  onlyAvailable() {
    this.movieService.getAllAvailableMovies()
    .subscribe((data: Movie[]) => {
      this.movies = data;
    });
  }

}
