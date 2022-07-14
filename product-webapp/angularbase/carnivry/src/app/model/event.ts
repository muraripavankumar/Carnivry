import { Binary } from "@angular/compiler";
import { EventTiming } from "./event-timing";
import { Seat } from "./seat";
import { Venue } from "./venue";

export class Event {
    public eventId: string;
    public title: string;
    public userEmailId: string;
    public eventDescription: string;
    public artist: string[];
    public genre: string[];
    public languages: string[];
    public eventTimings: EventTiming;
    public poster: Binary;
    public fileName: string;
    public fileType: string;
    public venue: Venue;
    public revenueGenerated: number;
    public ticketsSold: number;
    public totalSeats: number;
    public seats: Seat[];
    public likes: number;
    public emailOfUsersLikedEvent: string[];
}
