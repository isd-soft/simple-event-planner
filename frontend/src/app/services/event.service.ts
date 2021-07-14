import { Injectable } from '@angular/core';
import { Event } from '../models/Event';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from 'rxjs';
import { AuthService } from './auth.service';
import { Header } from '@syncfusion/ej2-navigations';
@Injectable({
  providedIn: 'root'
})

export class EventService {
  private token = sessionStorage.getItem('TOKEN_KEY');
  private authService: AuthService;
  private readonly apiURL: string = "http://localhost:8080/events";
  constructor(private http: HttpClient) {
    this.authService = new AuthService(http);
  }

  getEvents(): Observable<Event[]> {
    const header = (this.authService.isAuthenticated) ? { Authorization: `Bearer ${this.token}` } : undefined;
    return this.http.get<Event[]>(this.apiURL,{headers:header})
    
  }
  
}