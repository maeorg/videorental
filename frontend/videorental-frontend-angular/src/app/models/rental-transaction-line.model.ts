import { Customer } from "./customer.model";
import { Movie } from "./movie.model";

export class RentalTransactionLine {
    constructor(
        public movie:Movie,
        public daysRented:number,
        public price:number,
        public transactionFinished:boolean,
        public createdDate:Date,
        public lastModifiedDate:Date,
        public id?:string
    ) {}
}
