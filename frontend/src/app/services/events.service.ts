import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  APPROVE_EVENT_URL,
  EVENTS_URL,
  MY_EVENTS_URL,
  APPROVED_EVENTS_URL,
} from '../urls.config';
import { EventModel } from '../models/event.model';

@Injectable({
  providedIn: 'root',
})
export class EventsService {
  constructor(private httpClient: HttpClient) {}

  getAllEvents(): Observable<any[]> {
    return this.httpClient.get<any[]>(EVENTS_URL);
  }

  getMyEvents(): Observable<any[]> {
    return this.httpClient.get<any[]>(MY_EVENTS_URL);
  }

  getApprovedEvents(): Observable<any[]> {
    return this.httpClient.get<any[]>(APPROVED_EVENTS_URL);
  }

  getEvent(id: number): Observable<EventModel> {
    return this.httpClient.get<EventModel>(`${EVENTS_URL}/${id}`);
  }

  createEvent(event: EventModel, params?: HttpParams): Observable<any> {
    return this.httpClient.post(EVENTS_URL, event, {
      params: params,
      responseType: 'text',
    });
  }

  changeEvent(event: EventModel, eventId: number): Observable<any> {
    return this.httpClient.put(EVENTS_URL + '/' + eventId, event, {
      responseType: 'text',
      observe: 'response',
    });
  }

  approveEvent(eventId: number) {
    return this.httpClient.put(APPROVE_EVENT_URL + '/' + eventId, null, {
      responseType: 'text',
    });
  }

  declineEvent(eventId: number) {
    return this.httpClient.delete(EVENTS_URL + '/' + eventId, {
      responseType: 'text',
      observe: 'response',
    });
  }
}
