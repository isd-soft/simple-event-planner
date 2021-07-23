import {UserShortModel} from "./user-short.model";

export interface Comment {
  id?: number;
  eventId?: number;
  creator?: UserShortModel;
  content: string;
  creation_date?: string;
}
