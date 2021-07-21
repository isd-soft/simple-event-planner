import {EventEmitter, Injectable} from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginModel } from "../models/login.model";
import {catchError, map} from "rxjs/operators";
import { of } from "rxjs";
import {UserModel} from "../models/user.model";
import {SIGN_IN_URL, SING_UP_URL, USERS_FULL_URL} from "../urls.config";
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly USER_KEY: string = "user";
  private readonly TOKEN_KEY: string = "jwtToken";
  constructor(private httpClient: HttpClient) {
    this.token = sessionStorage.getItem(this.TOKEN_KEY);

    let userStr = sessionStorage.getItem(this.USER_KEY);
    if (userStr !== null) {
      this.user = JSON.parse(userStr);
    }
  }

  private user: any = null;
  private token: string | null = null;
  private eventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();

  private setAuth(token: string | null, user: UserModel | null) {
    this.token = token;
    this.user = user;

    if (token !== null) {
      sessionStorage.setItem(this.TOKEN_KEY, token);
    }

    if (user !== null) {
      sessionStorage.setItem(this.USER_KEY, JSON.stringify(user));
    }

    this.emit();
  }

  private emit() {
    this.eventEmitter.emit(this.isAuthenticated());
  }

  subscribe(callback: Function) {
    this.eventEmitter.subscribe(callback);
  }

  getUserRole(){
    return this.user.role;
  }
  isAuthenticated() {
    return this.getToken() !== null;
  }

  getToken(): string | null {
    return this.token;
  }

  getUser():any {
    return this.user;
  }

  isAdmin(): boolean {
    return this.user !== null && this.user.role == "ADMIN";
  }

  login(loginModel : LoginModel) {
    return this.httpClient.post(SIGN_IN_URL, loginModel, {
      observe: 'response',
      responseType: 'blob'
    }).toPromise()
      .then((response: any) => response.headers.get("Authorization"))
      .then(token => {
        this.httpClient.get<UserModel>(USERS_FULL_URL, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }).toPromise()
          .then(user => this.setAuth(token, user))
          .then(() => this.emit())
      })
  }

  logout(): void {
    this.token = null;
    this.user = null;

    sessionStorage.removeItem(this.TOKEN_KEY);
    sessionStorage.removeItem(this.USER_KEY);

    this.emit();
  }

  register(user: UserModel): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.httpClient.post(SING_UP_URL, user, {
        observe: 'response',
        responseType: 'blob'
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
