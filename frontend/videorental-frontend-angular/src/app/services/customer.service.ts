import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private url = environment.baseUrl + "/api/v1/customer"

  constructor(private httpClient: HttpClient,
    private authService: AuthService) { }

  getAllCustomers() {
    return this.httpClient.get<Customer[]>(this.url, this.authService.getAuthToken());
  }

  getCustomerById(customerId: string) {
    return this.httpClient.get<Customer>(this.url + "/" + customerId, this.authService.getAuthToken());
  }

  addCustomer(customer: Customer) {
    return this.httpClient.post(this.url, customer, this.authService.getAuthToken());
  }
}
