import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { MY_EVENTS_URL } from "../urls.config";
import {EventModel} from "../models/event.model";

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(private httpClient: HttpClient) { }

  getMyEvents(): Observable<EventModel[]> {
    return this.httpClient.get<EventModel[]>(MY_EVENTS_URL);
  }

  getEvent(id: number): Observable<EventModel> {
    return this.httpClient.get<EventModel>(`${MY_EVENTS_URL}/${id}`);
  }

}
