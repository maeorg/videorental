import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { RentalTransaction } from '../models/rental-transaction.model';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private url = environment.baseUrl + "/api/v1/rental"

  constructor(private httpClient: HttpClient) { }

  getAllRentalTransactions() {
    return this.httpClient.get<RentalTransaction[]>(this.url);
  }

  addRental(rentalTransaction: RentalTransaction) {
    return this.httpClient.post(this.url, rentalTransaction);
  }
}
