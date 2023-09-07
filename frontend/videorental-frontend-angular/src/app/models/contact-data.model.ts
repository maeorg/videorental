export class ContactData {
    constructor(
        public email:string,
        public phoneNumber:string,
        public lastModifiedDate:Date,
        public contactData:ContactData,
        public id?:string
    ) {}
}
