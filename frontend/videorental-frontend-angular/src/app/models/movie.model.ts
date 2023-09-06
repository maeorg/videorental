export class Movie {
    constructor(
        public title:string,
        public releaseYear:number,
        public lengthInMinutes:number,
        // public movieType:Movietype,
        public rentedOut:boolean,
        // public genres:Genres,
        public createdDate:Date,
        public lastModifiedDate:Date,
        // TODO directors, stars, writers
        public id?:string
    ) {}
}
