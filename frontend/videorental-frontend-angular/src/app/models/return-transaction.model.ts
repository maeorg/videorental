import { Movie } from "./movie.model";
import { RentalTransactionLine } from "./rental-transaction-line.model";

export class ReturnTransaction {
    constructor(
        public totalSum:number,
        public createdDate:Date,
        public movie:Movie,
        public rentalTransactionLine:RentalTransactionLine,
        public lateFee:number,
        public lastModifiedDate:Date,
        public id?:string
    ) {}
}
