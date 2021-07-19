import {Injectable} from '@angular/core';
import {Event} from '../models/event';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from 'rxjs';
import {AuthService} from './auth.service';
import {Header} from '@syncfusion/ej2-navigations';

@Injectable({
  providedIn: 'root'
})

export class EventService {
  private status: string;
  private token = sessionStorage.getItem('TOKEN_KEY');
  private authService: AuthService;
  private readonly GetEvents: string = "http://localhost:8080/events";
  private readonly GetEvent: string = "http://localhost:8080/events/";

  constructor(private http: HttpClient) {
    this.authService = new AuthService(http);
  }

  getEvents(): Observable<Event[]> {
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    return this.http.get<Event[]>(this.GetEvents, {headers: header});

  }

  getEventById(id: number): Observable<Event> {
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    return this.http.get<Event>(`${this.GetEvent}${id}`, {headers: header});
  }

  deleteEventById(id: number): void {
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    this.http.delete(`${this.GetEvent}${id}`, {headers: header})
      .subscribe(() => this.status = 'Delete successful');
  }

}
