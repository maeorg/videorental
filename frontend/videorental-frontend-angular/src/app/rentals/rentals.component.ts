import { Component } from '@angular/core';
import { RentalTransaction } from '../models/rental-transaction.model';
import { RentalService } from '../services/rental.service';

@Component({
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.scss']
})
export class RentalsComponent {

  rentalTransactions: RentalTransaction[] = [];

  constructor(private rentalService: RentalService) {}

  ngOnInit() {
    this.rentalService.getAllRentalTransactions()
    .subscribe((data: RentalTransaction[]) => {
      this.rentalTransactions = data;
    });
  }

}
