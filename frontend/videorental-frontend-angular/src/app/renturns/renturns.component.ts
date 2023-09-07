import { Component } from '@angular/core';
import { ReturnTransaction } from '../models/return-transaction.model';
import { ReturnService } from '../services/return.service';

@Component({
  selector: 'app-renturns',
  templateUrl: './renturns.component.html',
  styleUrls: ['./renturns.component.scss']
})
export class RenturnsComponent {

  returnTransactions: ReturnTransaction[] = [];

  constructor(private returnService: ReturnService) {}

  ngOnInit() {
    this.returnService.getAllReturnTransactions()
    .subscribe((data: ReturnTransaction[]) => {
      this.returnTransactions = data;
    });
  }
}
