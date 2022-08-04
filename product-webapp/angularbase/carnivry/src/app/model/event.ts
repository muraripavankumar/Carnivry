import { EventTiming } from "./event-timing";
import { Seat } from "./seat";
import { Venue } from "./venue";

export class Event {
    public eventId: string;
    public title: string;
    public userName:string;
    public userEmailId: string;
    public eventDescription: string;
    public artists: string[];
    public genre: string[];
    public languages: string[];
    public eventTimings: EventTiming;
    public posters: string[];
    public venue: Venue;
    public revenueGenerated: number;
    public ticketsSold: number;
    public totalSeats: number;
    public seats: Seat[];
    public likes: number;
    public emailOfUsersLikedEvent: string[];
    
}
