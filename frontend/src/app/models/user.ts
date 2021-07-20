import { EventsService } from "../services/events.service";

export interface User{
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    age: number;
    phoneNumber: string;
    role : string;
    hostedEvents: Event[];
}