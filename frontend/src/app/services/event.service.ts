import {Injectable} from '@angular/core';
import {Event} from '../models/event';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from 'rxjs';
import {AuthService} from './auth.service';
import {Header} from '@syncfusion/ej2-navigations';
import { LoginModel } from '../models/login.model';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})

export class EventService {
  private role: string ;

  private user: User;
  private status: string;
  private token = sessionStorage.getItem('TOKEN_KEY');
  private authService: AuthService;
  private readonly GetEventsUser: string = "http://localhost:8080/events/approved";
  private readonly GetEventsAdmin: string = "http://localhost:8080/events";
  private readonly GetEvent: string = "http://localhost:8080/events/";
  private readonly GetUser: string = "http://localhost:8080/users/";

  constructor(private http: HttpClient) {
    this.authService = new AuthService(http);
    
  }

    SetUserRole(){
      this.getUser().subscribe(user => this.user = user);
      this.role = this.user.role;
    }

   private getUser(): Observable<User>{
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    let userLogin: LoginModel = this.authService.getUser();
    return this.http.get<User>(`${this.GetUser}${userLogin.email}`, {headers: header});
    
  }
  getEvents(): Observable<Event[]> {
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    if(this.role == "ADMIN"){
      return this.http.get<Event[]>(this.GetEventsAdmin, {headers: header});
    }
    return this.http.get<Event[]>(this.GetEventsUser, {headers: header});
    
    

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
