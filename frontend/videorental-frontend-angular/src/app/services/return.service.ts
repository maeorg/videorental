import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { ReturnTransaction } from '../models/return-transaction.model';

@Injectable({
  providedIn: 'root'
})
export class ReturnService {

  private url = environment.baseUrl + "/api/v1/return"

  constructor(private httpClient: HttpClient) { }

  getAllReturnTransactions() {
    return this.httpClient.get<ReturnTransaction[]>(this.url);
  }

  addReturn(returnTransaction: ReturnTransaction) {
    return this.httpClient.post(this.url, returnTransaction);
  }
}
