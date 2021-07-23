import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Statistics } from '../models/statistics';
import { STATISTICS_URL } from '../urls.config';
import { Health } from '../models/Health.model';
import { AuthService } from './auth.service';
import { App } from '../models/app.model';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  private readonly GetHealthStatus: string = "http://localhost:8080/actuator/health";
  private readonly GetAppInfo: string = "http://localhost:8080/actuator/info";
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
   return this.http.get<any>(this.GetHealthStatus, {headers: header});
  }
  getAppInfo():Observable<App>{
    const header = (this.authService.isAuthenticated) ? {Authorization: `Bearer ${this.token}`} : undefined;
    return this.http.get<App>(this.GetAppInfo, {headers: header});
  }
}
