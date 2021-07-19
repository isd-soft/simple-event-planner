import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable} from "rxjs";
import { CATEGORIES_URL } from "../urls.config";
import { EventCategory } from "../models/event-category.model";

@Injectable({
  providedIn: 'root'
})
export class EventCategoriesService {

  constructor(private httpClient: HttpClient) { }

  getAllCategories(): Observable<EventCategory[]> {
    return this.httpClient.get<EventCategory[]>(CATEGORIES_URL);
  }
}
