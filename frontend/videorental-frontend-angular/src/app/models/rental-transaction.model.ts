import { Customer } from "./customer.model";
import { RentalTransactionLine } from "./rental-transaction-line.model";

export class RentalTransaction {
    constructor(
        public customer:Customer,
        public rentalTransactionLines:RentalTransactionLine[],
        public totalSum:number,
        public createdDate:Date,
        public lastModifiedDate:Date,
        public id?:string
    ) {}
}
