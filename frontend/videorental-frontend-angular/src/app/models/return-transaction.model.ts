import { Movie } from "./movie.model";
import { RentalTransactionLine } from "./rental-transaction-line.model";

export class ReturnTransaction {
    constructor(
        public createdDate:Date,
        public lastModifiedDate:Date,
        public rentalTransactionLine:RentalTransactionLine,
        public movie:Movie,
        public lateFee:number,
        public id?:string
    ) {}
}
