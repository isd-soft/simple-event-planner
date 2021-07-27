import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
import {EventService} from 'src/app/services/event.service';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';
import { Attachment } from 'src/app/models/attachment.model';
import { AttachmentsService } from 'src/app/services/attachments.service';
import {EventModel} from "../../models/event.model";
import {EventsService} from "../../services/events.service";
import {AuthService} from "../../services/auth.service";
import { Comment } from "../../models/comment.model";
import {CommentsService} from "../../services/comments.service";
import {ReactionsService} from "../../services/reactions.service";
import { EVENTS } from 'src/app/urls.config';

@Component({
  selector: 'app-event-info',
  templateUrl: './event-info.component.html',
  styleUrls: ['./event-info.component.css'],
})
export class EventInfoComponent implements OnInit {
  private readonly COVER_PHOTO_NAME: string = "cover_photo.jpg";

  attendeeEmails: string[] = [];
  event: any;
  attachments: Attachment[] = [];
  linkAttachments: string[] = [];
  role: string;
  userId: any;
  comments: Comment[] = [];
  constructor(public dialog: MatDialog,
              public authService: AuthService,
              private eventService: EventService,
              private eventsService: EventsService,
              private attachmentService: AttachmentsService,
              private commentsService: CommentsService,
              private reactionsService: ReactionsService,
              private route: ActivatedRoute,
              private router: Router) {

    let stringId = this.route.snapshot.paramMap.get('id');
    if (stringId != null) {
      let id: number = parseInt(stringId);
      this.eventsService.getEvent(id).subscribe((event: EventModel) => {
        console.log(event);
        this.comments = event.comments;
        this.event = event;
        this.attachments = event.attachments.filter(attachment => attachment.name !== this.COVER_PHOTO_NAME);
        this.getCover(event.attachments.find(attachment => attachment.name === this.COVER_PHOTO_NAME));
        this.role = this.eventService.getRole();
        this.userId = this.eventService.getUserId();
        this.linkAttachments = event.links.map(linkObject => linkObject.link);
        this.allReactions = event.eventReactions.map(reaction =>  ({
          ...reaction,
          index: this.reactions.findIndex(r => r.type == reaction.type)
        }))

        this.myReaction = this.allReactions.find(x => x.creator.email === authService.getUser().email);
      });
    }
  }

  redirection() {
    this.router.navigate(["/events"]).then(() => {
      window.location.reload(); 
   });
  }

  openDialog() {
    this.dialog.open(DialogComponent, {
      data: {array: this.event.attendees}
    });
  }

  deleteEvent() {
    let id = this.event.id;
    if (id != null) {
      this.eventService.deleteEventById(id);
       this.redirection();
    }
  }

  openLinkAttachment(link: string) {
    window.open(link);
  }

  showLink(link: string) {
    // @ts-ignore
    return link.match(/:\/\/(.[^/]+)/)[1];
  }

  editEvent(): void{
    this.router.navigate(["/update-event/"+ this.event.id]);
  }

  convert(x: any) {
    return new Date(x).toLocaleString();
  }

  getCover(image: any){
    let element: any = document.getElementById("mat-card-image");
    this.attachmentService.setImageFromAttachment(image, element)
      .catch(console.log);
  }

  downloadAttachment(attachment: Attachment) {
    this.attachmentService.downloadAttachment(attachment)
      .catch(console.log);
  }

  ngOnInit(): void {
  }


  isCommentReadonly() {
    let isAdmin = this.authService.isAdmin();
    let creatorEmail = this.authService.getUser().email;

    return (comment: Comment) => !(isAdmin || (comment.creator && creatorEmail === comment.creator.email));
  }

  addComment(comment: Comment) {
    this.commentsService.addComment(comment, this.event.id).toPromise()
      .then((createdComment: Comment) => {
        Object.assign(comment, createdComment);
      })
      .catch(console.log);
  }

  updateComment(comment: Comment) {
    if (!comment.id) return;
    this.commentsService.updateComment(comment, comment.id).toPromise()
      .catch(console.log);
  }

  deleteComment(comment: Comment) {
    if (!comment.id) return;
    this.commentsService.deleteComment(comment.id).toPromise()
      .catch(console.log);
  }



  reactions = [
    {
      icon: 'thumb_up',
      style: "color:#f5f56e",
      type: 'LIKE'
    },
    {
      icon: 'thumb_down',
      style: "color:#f5f56e",
      type: 'DISLIKE'
    },
  ]


  allReactions: any[] = []
  myReaction: any = null;

  selectReaction(reaction: any) {
    this.reactionsService.addEventReaction(reaction, this.event.id).toPromise()
      .then(r => Object.assign(reaction, r))
      .catch(console.log)
  }
}
