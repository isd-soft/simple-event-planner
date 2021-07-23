import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import { Comment } from '../../../models/comment.model';
import { UserShortModel } from "../../../models/user-short.model";

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit {

  @Input() comments: Comment[];
  @Input() isReadonly: Function = () => true;

  @Output() onCreate = new EventEmitter<Comment>();
  @Output() onEdit = new EventEmitter<Comment>();
  @Output() onDelete = new EventEmitter<Comment>();

  addComment() {
    let element: any = document.getElementById("comment-insert-area");
    let content = element.value.trim();

    if (!content) return;

    const comment: Comment = {
      content: content,
    };

    this.comments.push(comment);
    this.onCreate.emit(comment);

    element.value = '';
  }

  deleteComment(comment: Comment, index: number) {
    this.onDelete.emit(comment);
    this.comments.splice(index, 1);
  }

  editComment(comment: Comment, content: string) {
    this.onEdit.emit(comment);
  }

  ngOnInit(): void {
  }

}
