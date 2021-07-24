import { EventCategory } from './event-category.model';
import { Attendee } from './attendee.model';
import { Attachment } from './attachment.model';
import { Comment } from './comment.model';

export interface EventModel {
  id: number;
  name: string;
  location: string;
  eventCategory: EventCategory;
  description: string;
  startDateTime: Date;
  endDateTime: Date;
  attendees: Attendee[];
  isApproved: boolean;
  image: any;
  attachments: Attachment[];
  links: any[];
  comments: Comment[];
  eventReactions: any[];
}
