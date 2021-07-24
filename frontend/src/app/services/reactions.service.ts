import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {COMMENT_REACTION_URL, EVENT_REACTION_URL} from "../urls.config";

@Injectable({
  providedIn: 'root'
})
export class ReactionsService {

  constructor(private httpClient: HttpClient) { }

  addEventReaction(reaction: any, eventId: number) {
    return this.httpClient.post<any>(EVENT_REACTION_URL + '/' + eventId, {
      type: reaction.type,
    });
  }

  addCommentReaction(reaction: any, commentId: number) {
    return this.httpClient.post<any>(COMMENT_REACTION_URL + '/' + commentId, {
      type: reaction.type,
    });
  }
}
