import {EventEmitter, Injectable} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginModel } from "../models/login.model";
import {catchError, map} from "rxjs/operators";
import { of } from "rxjs";
import {UserModel} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly BASE_URL: string = "http://localhost:8080";
  private readonly SIGN_IN_URL: string = "/login";
  private readonly SING_UP_URL: string = "/register";
  private readonly TOKEN_KEY: string = "jwtToken";

  constructor(private httpClient: HttpClient) { }

  private token: string | null = null;
  private eventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();

  private clearToken(): void {
    this.token = null;
    sessionStorage.removeItem(this.TOKEN_KEY);
    this.emit();
  }

  private setToken(token: string): void {
    this.token = token;
    sessionStorage.setItem(this.TOKEN_KEY, token);
    this.emit();
  }

  private emit() {
    this.eventEmitter.emit(this.isAuthenticated());
  }

  subscribe(callback: Function) {
    this.eventEmitter.subscribe(callback);
  }

  isAuthenticated() {
    return this.getToken() !== null;
  }

  getToken(): string | null {
    // TODO: set back
    return this.token;// || sessionStorage.getItem(this.TOKEN_KEY);
  }

  login(loginModel : LoginModel) {
    return new Promise<void>((resolve, reject) => {
      this.httpClient.post<any>(this.BASE_URL + this.SIGN_IN_URL, loginModel, {
        observe: 'response'
      })
        .pipe(
          map((x: any) => x.headers.get("Authorization")),

          catchError(err => {
            reject(err);
            return of(null);
          })

        )
        .subscribe((token: string) => {
          this.setToken(token);
          resolve();
        });
    })
  }

  logout(): void {
    this.clearToken();
  }

  register(user: UserModel): Promise<any> {
      return new Promise<any>((resolve, reject) => {
      this.httpClient.post<any>(this.BASE_URL + this.SING_UP_URL, user, {
        observe: 'response'
      })
        .pipe(
          map((x: any) => x.statusText),

          catchError(err => {
            reject(err);
            return of(null);
          })
        )
        .subscribe(x => resolve(x));
    })
  }

}
