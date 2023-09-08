import { Component } from '@angular/core';
import { RentalTransaction } from '../models/rental-transaction.model';
import { RentalService } from '../services/rental.service';
import { NgForm } from '@angular/forms';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { RentalTransactionLine } from '../models/rental-transaction-line.model';
import { MovieService } from '../services/movie.service';
import { Movie } from '../models/movie.model';

@Component({
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.scss']
})
export class RentalsComponent {

  rentalTransactions: RentalTransaction[] = [];
  customers: Customer[] = [];
  movies: Movie[] = [];
  customer!: Customer;
  movie!: Movie;

  constructor(private rentalService: RentalService,
    private customerService: CustomerService,
    private movieService: MovieService) { }

  ngOnInit() {
    this.rentalService.getAllRentalTransactions()
      .subscribe((data: RentalTransaction[]) => {
        this.rentalTransactions = data;
      });

    this.customerService.getAllCustomers()
      .subscribe((data: Customer[]) => {
        this.customers = data;
      });

    this.movieService.getMovies()
      .subscribe((data: Movie[]) => {
        this.movies = data;
      });

  }

  handleSubmit(addRentalTransactionForm: NgForm) {
    const formValue = addRentalTransactionForm.value;

    this.customers.forEach(element => {
      if (element.id == formValue.customerId) {
        this.customer = element;
      }
    });

    this.movies.forEach(element => {
      if (element.id == formValue.movieId) {
        this.movie = element;
      }
    });

    let rentalTransactionLines: RentalTransactionLine[] = [];
    rentalTransactionLines.push(new RentalTransactionLine(
      this.movie,
      formValue.daysRented,
      0,
      false,
      new Date(),
      new Date(),
    ));

    const newRentalTransaction = new RentalTransaction(
      this.customer,
      rentalTransactionLines,
      0,
      new Date(),
      new Date()
    );

    this.rentalService.addRental(newRentalTransaction).subscribe(data => {
      this.rentalService.getAllRentalTransactions().subscribe(result => {
        this.rentalTransactions = result;
      })
    });
  }

}
