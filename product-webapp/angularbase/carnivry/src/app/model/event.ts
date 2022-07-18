import { EventTiming } from "./event-timing";
import { Seat } from "./seat";
import { Venue } from "./venue";

export class Event {
    public eventId: string;
    public title: string;
    public userEmailId: string;
    public eventDescription: string;
    public artists: string[];
    public genre: string[];
    public languages: string[];
    public eventTimings: EventTiming;
    public poster: any;
    public fileName: string;
    public fileType: string;
    public venue: Venue;
    public revenueGenerated: number;
    public ticketsSold: number;
    public totalSeats: number;
    public seats: Seat[];
    public likes: number;
    public emailOfUsersLikedEvent: string[];

    constructor(eventId:string, title:string,eventDescription: string,artist:string[], genre:string[],languages:string[],eventTimings: EventTiming,venue: Venue,totalSeats: number,seats: Seat[]){
        this.eventId=eventId;
        this.title=title;
        this.eventDescription=eventDescription;
        this.artists=artist;
        this.genre=genre;
        this.languages=languages;
        this.eventTimings=eventTimings;
        this.totalSeats=totalSeats;
        this.venue=venue;
        this.seats=seats;
    }
    
}
