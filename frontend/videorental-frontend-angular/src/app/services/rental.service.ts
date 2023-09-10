import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RentalTransaction } from '../models/rental-transaction.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private url = environment.baseUrl + "/api/v1/rental"

  constructor(private httpClient: HttpClient,
    private authService: AuthService) { }


  getAllRentalTransactions() {
    return this.httpClient.get<RentalTransaction[]>(this.url, this.authService.getAuthToken());
  }

  addRental(rentalTransaction: RentalTransaction) {
    return this.httpClient.post(this.url, rentalTransaction, this.authService.getAuthToken());
  }
}
