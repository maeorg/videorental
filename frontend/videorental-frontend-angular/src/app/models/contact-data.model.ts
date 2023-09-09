export class ContactData {
    constructor(
        public email:string,
        public phoneNumber:string,
        public createdDate:Date,
        public lastModifiedDate:Date,
        public id?:string
    ) {}
}
