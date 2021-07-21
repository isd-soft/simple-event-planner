import {UserModel} from "src/app/models/user.model";
import { Attachment } from "./attachment.model";
import {Attendee} from "./Attendee";
import {EventCategory} from "./EventCategory";

export interface Event {
  id?: number;
  name: string;
  location: string;
  startDateTime: string;
  endDateTime: string;
  description: string;
  isApproved: boolean;
  host: UserModel;
  attendees: Attendee[];
  eventCategory: EventCategory;
  attachments: Attachment[];
}
