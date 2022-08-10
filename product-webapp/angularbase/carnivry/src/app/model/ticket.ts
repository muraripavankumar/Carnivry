import { EventTiming } from "./event-timing";
import { Seat } from "./seat";
import { Venue } from "./venue";

export class Ticket {
    public eventId: string;
    public title: string;
    public email: string;
    public image: string;
    public host: string;
    public description: string;
    public artists: string[];
    public timings: EventTiming;
    public venue: Venue;
    public seats: Seat[];
}
