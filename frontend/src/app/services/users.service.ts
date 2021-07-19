import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { UserShortModel } from "../models/user-short.model";
import {USERS_URL} from "../urls.config";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<UserShortModel[]> {
    return this.httpClient.get<UserShortModel[]>(USERS_URL);
  }

}
