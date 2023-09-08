import { Component } from '@angular/core';
import { ReturnTransaction } from '../models/return-transaction.model';
import { ReturnService } from '../services/return.service';
import { Movie } from '../models/movie.model';
import { MovieService } from '../services/movie.service';
import { RentalTransactionLine } from '../models/rental-transaction-line.model';
import { RentalService } from '../services/rental.service';
import { RentalTransaction } from '../models/rental-transaction.model';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-renturns',
  templateUrl: './renturns.component.html',
  styleUrls: ['./renturns.component.scss']
})
export class RenturnsComponent {

  returnTransactions: ReturnTransaction[] = [];
  rentalTransactions: RentalTransaction[] = [];
  rentalTransactionLine!: RentalTransactionLine;
  movie!: Movie;
  movies: Movie[] = [];

  constructor(private returnService: ReturnService,
    private movieService: MovieService,
    private rentalService: RentalService) { }

  ngOnInit() {
    this.returnService.getAllReturnTransactions()
      .subscribe((data: ReturnTransaction[]) => {
        this.returnTransactions = data;
      });

    this.movieService.getMovies()
      .subscribe((data: Movie[]) => {
        this.movies = data;
      });

    this.rentalService.getAllRentalTransactions()
      .subscribe((data: RentalTransaction[]) => {
        this.rentalTransactions = data;
      });

  }

  handleSubmit(addReturnTransactionForm: NgForm) {
    const formValue = addReturnTransactionForm.value;

    this.movies.forEach(element => {
      if (element.id == formValue.movieId) {
        this.movie = element;
      }
    });

    this.rentalTransactions.forEach(transaction => {
      transaction.rentalTransactionLines.forEach(transactionLine => {
        if (transactionLine.movie.id == formValue.movieId) {
          this.rentalTransactionLine = transactionLine;
        }
      });
    });

    const returnTransaction = new ReturnTransaction(
      new Date(),
      new Date(),
      this.rentalTransactionLine,
      this.movie,
      0
    );

    this.returnService.addReturn(returnTransaction).subscribe(data => {
      this.returnService.getAllReturnTransactions().subscribe(result => {
        this.returnTransactions = result;
      });
    });
  }
}
