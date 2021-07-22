import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
import {EventService} from 'src/app/services/event.service';
import {ActivatedRoute} from '@angular/router';
import {Event} from 'src/app/models/event';
import {Router} from '@angular/router';
import { Attachment } from 'src/app/models/attachment.model';
import { AttachmentsService } from 'src/app/services/attachments.service';
@Component({
  selector: 'app-event-info',
  templateUrl: './event-info.component.html',
  styleUrls: ['./event-info.component.css'],
})
export class EventInfoComponent implements OnInit {
  private readonly COVER_PHOTO_NAME: string = "cover_photo.jpg";

  attendeeEmails: string[] = [];
  event: Event;
  attachments: Attachment[] = [];
  role: string;

  constructor(public dialog: MatDialog, private eventService: EventService,private attachmentService: AttachmentsService, private route: ActivatedRoute, private router: Router) {
    let stringId = this.route.snapshot.paramMap.get('id');
    if (stringId != null) {
      let id: number = parseInt(stringId);
      this.eventService.getEventById(id).subscribe((event) => {
        this.event = event
        this.attachments = event.attachments.filter(attachment => attachment.name !== this.COVER_PHOTO_NAME);
        this.getCover(event.attachments.find(attachment => attachment.name === this.COVER_PHOTO_NAME));
        this.role = this.eventService.getRole();
      });
    }
  }

  redirection() {
    this.router.navigate(["/events"])
      .catch(console.log)
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
}
