import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { LoginModel } from "../models/login.model";
import {catchError, map} from "rxjs/operators";
import { of } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly BASE_URL: string = "http://localhost:8080";
  private readonly SIGN_IN_URL: string = "/login";
  private readonly TOKEN_KEY: string = "jwtToken";

  constructor(private httpClient: HttpClient) { }

  private token: string | null = null;

  public isAuthenticated() {
    return this.getToken() !== null;
  }

  public getToken(): string | null {
    // TODO: set back
    return this.token;// || sessionStorage.getItem(this.TOKEN_KEY);
  }

  private clearToken(): void {
    this.token = null;
    sessionStorage.removeItem(this.TOKEN_KEY);
  }

  private setToken(token: string): void {
    this.token = token;
    sessionStorage.setItem(this.TOKEN_KEY, token);
  }

  public login(loginModel : LoginModel) {
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
}
