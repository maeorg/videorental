import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private url = environment.baseUrl + "/api/v1/movie"

  constructor(private httpClient: HttpClient) { }

  getMovies() {
    return this.httpClient.get<Movie[]>(this.url);
  }

  getAllAvailableMovies() {
    return this.httpClient.get<Movie[]>(this.url + "/available");
  }

  getMovieById(movieId: string) {
    return this.httpClient.get<Movie>(this.url + "/" + movieId);
  }

}
