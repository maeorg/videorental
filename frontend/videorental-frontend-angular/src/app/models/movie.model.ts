import { Actor } from "./actor.model";
import { Director } from "./director.model";
import { Genre } from "./genre.model";
import { MovieType } from "./movietype.model";
import { Writer } from "./writer.model";

export class Movie {
    constructor(
        public title:string,
        public releaseYear:number,
        public lengthInMinutes:number,
        public movieType:MovieType,
        public rentedOut:boolean,
        public genres:Genre[],
        public createdDate:Date,
        public lastModifiedDate:Date,
        public directors:Director[],
        public stars:Actor[],
        public writers:Writer[],
        public id?:string
    ) {}
}
