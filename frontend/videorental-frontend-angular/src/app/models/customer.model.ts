import { ContactData } from "./contact-data.model";

export class Customer {
    constructor(
        public firstName:string,
        public lastName:string,
        public username:string,
        public password:string,
        public bonusPoints:number,
        public createdDate:Date,
        public lastModifiedDate:Date,
        public contactData:ContactData,
        public id?:string
    ) {}
}
