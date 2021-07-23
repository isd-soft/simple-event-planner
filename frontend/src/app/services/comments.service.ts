import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { COMMENTS_URL } from "../urls.config";
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private httpClient: HttpClient) { }

  addComment(comment: Comment, eventId: number) {
    return this.httpClient.post<Comment>(`${COMMENTS_URL}/${eventId}` , comment);
  }

  updateComment(comment: Comment, commentId: number) {
    return this.httpClient.put(`${COMMENTS_URL}/${comment.id}`, {
      content: comment.content,
      id: commentId,
    });
  }

  deleteComment(commentId: number) {
    return this.httpClient.delete(`${COMMENTS_URL}/${commentId}`, {responseType: 'text'});
  }
}
