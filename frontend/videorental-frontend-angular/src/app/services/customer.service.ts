import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private url = environment.baseUrl + "/api/v1/customer"

  constructor(private httpClient: HttpClient) { }

  getAllCustomers() {
    return this.httpClient.get<Customer[]>(this.url);
  }

  getCustomerById(customerId: string) {
    return this.httpClient.get<Customer>(this.url + "/" + customerId);
  }
}
