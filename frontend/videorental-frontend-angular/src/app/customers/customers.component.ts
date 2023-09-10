import { Component } from '@angular/core';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { NgForm } from '@angular/forms';
import { ContactData } from '../models/contact-data.model';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.scss']
})
export class CustomersComponent {

  customers: Customer[] = [];

  constructor(private customerService: CustomerService) { }

  ngOnInit() {
    this.customerService.getAllCustomers()
      .subscribe((data: Customer[]) => {
        this.customers = data;
      });
  }

  handleSubmit(addCustomer: NgForm) {
    const formValue = addCustomer.value;

    const customer = new Customer(
      formValue.firstName,
      formValue.lastName,
      formValue.username,
      formValue.password,
      formValue.bonusPoints,
      new Date(),
      new Date(),
      new ContactData(
        formValue.email,
        formValue.phoneNumber,
        new Date(),
        new Date()
      )
    );

    this.customerService.addCustomer(customer).subscribe(data => {
      this.customerService.getAllCustomers().subscribe(result => {
        this.customers = result;
      });
    });
    addCustomer.resetForm();
  }
}
