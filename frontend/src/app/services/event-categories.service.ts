import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CATEGORIES_URL } from '../urls.config';
import { EventCategory } from '../models/event-category.model';

@Injectable({
  providedIn: 'root',
})
export class EventCategoriesService {
  constructor(private http: HttpClient) {}

  getAllCategories(): Observable<EventCategory[]> {
    return this.http.get<EventCategory[]>(CATEGORIES_URL);
  }

  postCategory(category: EventCategory): Observable<any> {
    return this.http.post(CATEGORIES_URL, category);
  }
}
