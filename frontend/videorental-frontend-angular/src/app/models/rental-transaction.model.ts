import { Customer } from "./customer.model";
import { RentalTransactionLine } from "./rental-transaction-line.model";

export class RentalTransaction {
    constructor(
        public totalSum:number,
        public createdDate:Date,
        public customer:Customer,
        public lastModifiedDate:Date,
        public rentalTransactionLines:RentalTransactionLine[],
        public id?:string
    ) {}
}
