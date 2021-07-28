import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Statistics } from '../models/statistics';
import { HEALTH_INFO_URL, HEALTH_STATUS_URL, STATISTICS_URL } from '../urls.config';
import { Health } from '../models/Health.model';
import { AuthService } from './auth.service';
import { App } from '../models/app.model';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  private authService: AuthService;
  private token = sessionStorage.getItem('TOKEN_KEY');
  constructor(private http: HttpClient) {
    this.authService = new AuthService(http);
  }

  getStatistics(): Observable<Statistics> {
    return this.http.get<Statistics>(STATISTICS_URL);
  }

  getHealth():Observable<any>{
   const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
   return this.http.get<any>(HEALTH_STATUS_URL, {headers: header});
  }
  getAppInfo():Observable<App>{
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    return this.http.get<App>(HEALTH_INFO_URL, {headers: header});
  }
}
