import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { Observable } from "rxjs";

import { EVENTS_URL, MY_EVENTS_URL } from "../urls.config";
import { EventModel } from "../models/event.model";

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(private httpClient: HttpClient) { }

  getMyEvents(): Observable<any[]> {
    return this.httpClient.get<any[]>(MY_EVENTS_URL);
  }

  getEvent(id: number): Observable<EventModel> {
    return this.httpClient.get<EventModel>(`${EVENTS_URL}/${id}`);
  }

  createEvent(event: EventModel, params?: HttpParams): Observable<any> {
    return this.httpClient.post(EVENTS_URL, event, {
      params: params,
      responseType: 'blob'
    });
  }
}
