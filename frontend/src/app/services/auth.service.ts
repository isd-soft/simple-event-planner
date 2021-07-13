import {EventEmitter, Injectable} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginModel } from "../models/login.model";
import {catchError, map} from "rxjs/operators";
import { of } from "rxjs";
import {UserModel} from "../models/user.model";
import {SIGN_IN_URL, SING_UP_URL} from "../urls.config";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY: string = "jwtToken";

  constructor(private httpClient: HttpClient) {
    this.token = sessionStorage.getItem(this.TOKEN_KEY);
  }

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
    return this.token;
  }

  login(loginModel : LoginModel) {
    return new Promise<void>((resolve, reject) => {
      this.httpClient.post<any>(SIGN_IN_URL, loginModel, {
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
      this.httpClient.post<any>(SING_UP_URL, user, {
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
