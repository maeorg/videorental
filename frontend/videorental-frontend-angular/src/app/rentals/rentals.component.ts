import { Component } from '@angular/core';
import { RentalTransaction } from '../models/rental-transaction.model';
import { RentalService } from '../services/rental.service';
import { FormArray, FormBuilder, FormGroup, NgForm } from '@angular/forms';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { RentalTransactionLine } from '../models/rental-transaction-line.model';
import { MovieService } from '../services/movie.service';
import { Movie } from '../models/movie.model';
import { EMPTY } from 'rxjs';

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

  movieIdForm: FormGroup;

  constructor(private rentalService: RentalService,
    private customerService: CustomerService,
    private movieService: MovieService,
    private movieIdFormBuilder: FormBuilder) {
    this.movieIdForm = this.movieIdFormBuilder.group({
      movieIds: this.movieIdFormBuilder.array([])
    })
  }

  movieIds(): FormArray {
    return this.movieIdForm.get("movieIds") as FormArray
  }

  newMovie(): FormGroup {
    return this.movieIdFormBuilder.group({
      movieId: '',
      daysRented: ''
    })
  }

  addMovie() {
    this.movieIds().push(this.newMovie());
  }

  removeMovie(i: number) {
    this.movieIds().removeAt(i);
  }


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

    let rentalTransactionLines: RentalTransactionLine[] = [];
    const moviesForm = this.movieIdForm.value;
    moviesForm.movieIds.forEach((elem: any) => {
      this.movies.forEach(element => {
        if (element.id == elem.movieId) {
          this.movie = element;
          rentalTransactionLines.push(new RentalTransactionLine(
            this.movie,
            elem.daysRented,
            0,
            false,
            new Date(),
            new Date(),
          ));
        }
      });
    });

    const formValue = addRentalTransactionForm.value;

    this.customers.forEach(element => {
      if (element.id == formValue.customerId) {
        this.customer = element;
      }
    });

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
