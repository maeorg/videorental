export class Actor {
    constructor(
        public firstName:string,
        public lastName:string,
        public birthYear:number,
        public createdDate:Date,
        public lastModifiedDate:Date,
        public id?:string
    ) {}
}
