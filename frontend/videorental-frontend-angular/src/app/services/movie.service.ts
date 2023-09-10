import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../models/movie.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private url = environment.baseUrl + "/api/v1/movie"

  constructor(private httpClient: HttpClient,
    private authService: AuthService) { }

  getMovies() {
    return this.httpClient.get<Movie[]>(this.url, this.authService.getAuthToken());
  }

  getAllAvailableMovies() {
    return this.httpClient.get<Movie[]>(this.url + "/available", this.authService.getAuthToken());
  }

  getMovieById(movieId: string) {
    return this.httpClient.get<Movie>(this.url + "/" + movieId, this.authService.getAuthToken());
  }

}
