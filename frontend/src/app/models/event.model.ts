export interface EventModel {
  id: number;
  name: string;
  location: string;
  category: string;
  description: string;
  startDateTime: string;
  endDateTime: string;
  attendees: string[];
  isApproved: string;
}
