import {AfterViewInit, Component, Input, Output, EventEmitter, OnInit} from '@angular/core';
import { Comment } from '../../../models/comment.model';
import {ReactionsService} from "../../../services/reactions.service";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements AfterViewInit, OnInit {

  @Input() comment: Comment;
  @Input() isReadonly: boolean = true;
  @Input() uid: number;
  @Input() commentReactions: any[] = [];

  @Output() onEdit = new EventEmitter<string>();
  @Output() onDelete = new EventEmitter();

  isEditing = false;
  editingComponent: any;

  allReactions: any[] = [];
  myReaction: any = null;

  constructor(private reactionsService: ReactionsService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.allReactions = this.comment.commentReactions || [];
    this.allReactions = this.allReactions.map(reaction =>  ({
      ...reaction,
      index: this.commentReactions.findIndex(r => r.type == reaction.type)
    }))

    this.myReaction = this.allReactions.find(reaction => reaction.creator.email == this.authService.getUser().email)
  }

  ngAfterViewInit(): void {
    this.editingComponent = document.getElementById('comment-content-editing' + this.uid);
  }

  selectReaction(reaction: any) {
    this.reactionsService.addCommentReaction(reaction, this.comment.id || -1).toPromise()
      .then(r => Object.assign(reaction, r))
      .catch(console.log);
  }

  startEditing() {
    this.isEditing = true;
  }

  edit() {
    let newContent = this.editingComponent.innerText;
    newContent.trim();

    if (newContent) {
      this.comment.content = newContent;
      this.editingComponent.innerText = newContent;
      this.onEdit.emit(newContent);
    }

    this.isEditing = false;
  }

  delete() {
    this.onDelete.emit();
  }

  showTime(): string {
    if (!this.comment.creation_date) {
      return '';
    }
    const now = new Date();
    const creationDate = new Date(this.comment.creation_date);

    let diff = now.getFullYear() - creationDate.getFullYear();
    if (diff === 1) return '1 year ago';
    if (diff > 1) return `${diff} years ago`;

    diff = now.getMonth() - creationDate.getMonth();
    if (diff === 1) return '1 month ago';
    if (diff > 1) return `${diff} months ago`;

    diff = now.getDay() - creationDate.getDay();
    if (diff === 1) return '1 day ago';
    if (diff > 1) return `${diff} days ago`;

    diff = now.getHours() - creationDate.getHours();
    if (diff === 1) return '1 hour ago';
    if (diff > 1) return `${diff} hours ago`;

    diff = now.getMinutes() - creationDate.getMinutes();
    if (diff === 1) return '1 minute ago';
    if (diff > 1) return `${diff} minutes ago`;

    return 'Just now';
  }
}
