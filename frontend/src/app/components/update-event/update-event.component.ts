import { Component, NgModule, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ElementRef, ViewChild } from '@angular/core';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { UsersService } from "../../services/users.service";
import { EventCategoriesService } from "../../services/event-categories.service";
import { EventsService } from "../../services/events.service";
import { EventCategory } from "../../models/event-category.model";
import {ActivatedRoute, Router} from "@angular/router";
import {UserShortModel} from "../../models/user-short.model";
import {EventModel} from "../../models/event.model";
import {Attendee} from "../../models/attendee.model";
import {Attachment} from "../../models/attachment.model";
import {AttachmentsService} from "../../services/attachments.service";

@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.css']
})
export class UpdateEventComponent implements OnInit {
  readonly ATTENDEE_SEPARATOR: number[] = [ENTER, COMMA];
  readonly COVER_PHOTO_NAME: string = "cover_photo.jpg";

  initialEvent: EventModel;

  event = new FormGroup({
    name: new FormControl('', [Validators.required]),
    location: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    startDateTime: new FormControl(new Date(), [Validators.required]),
    endDateTime: new FormControl(new Date(), [Validators.required])
  });

  coverImage: Attachment = {
    content: '',
    name: '',
    type: ''
  }

  imageElement: any;

  attachments: Attachment[] = [];
  categories: EventCategory[] = [];

  attendeeCtrl = new FormControl();
  filteredAttendees: Observable<string[]>;
  attendees: string[] = [];
  allAttendees: string[] = [];

  @ViewChild('attendeeInput')
  attendeeInput!: ElementRef<HTMLInputElement>;

  constructor(private usersService: UsersService,
              private eventCategoriesService: EventCategoriesService,
              private eventsService: EventsService,
              private attachmentsService: AttachmentsService,
              private router: Router,
              private activatedRouter: ActivatedRoute) {
    this.filteredAttendees = this.attendeeCtrl.valueChanges.pipe(
      startWith(null),
      map((attendee: string | null) => attendee ? this._filter(attendee) : this.allAttendees.slice()));

    eventCategoriesService.getAllCategories().toPromise()
      .then(categories => this.categories = categories)
      .catch(err => console.log(err));

    usersService.getAllUsers().toPromise()
      .then((users: UserShortModel[]) => this.allAttendees = users.map(user => user.email))
      .catch((err: any) => console.log(err));

  }

  ngOnInit(): void {
    this.imageElement = document.getElementById("event-cover-image");

    const eventId: number = this.activatedRouter.snapshot.params['id'];
    this.eventsService.getEvent(eventId).toPromise()
      .then((event: EventModel) => {
        this.initialEvent = event;
        this.event.patchValue({
          category: this.categories.findIndex(
            (category: EventCategory) => category.id === event.eventCategory.id
          ),
          ...event
        });

        this.attendees = event.attendees.map((attendee: Attendee) => attendee.email);
        this.attachments = event.attachments.filter(attachment => attachment.name !== this.COVER_PHOTO_NAME);

        let coverPhoto = event.attachments.find(attachment => attachment.name === this.COVER_PHOTO_NAME);
        if (!coverPhoto) {
          return;
        }

        let coverPhotoId = coverPhoto.id || Number.MAX_SAFE_INTEGER;

        this.attachmentsService.getAttachment(coverPhotoId).toPromise()
          .then(coverImage => {
            console.log(coverImage);
            this.coverImage = coverImage;
            // @ts-ignore
            this.attachmentsService.setImageFromAttachment(coverImage, this.imageElement);
          })
      });
  }

  addAttendee(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (this.validateEmail(value)) {
      this.attendees.push(value);
    }

    event.chipInput!.clear();
    this.attendeeCtrl.setValue(null);
  }

  removeAttendee(attendee: string): void {
    const index = this.attendees.indexOf(attendee);

    if (index >= 0) {
      this.attendees.splice(index, 1);
    }
  }

  selectAttendee(event: MatAutocompleteSelectedEvent): void {
    if (this.validateEmail(event.option.viewValue)) {
      this.attendees.push(event.option.viewValue);
    }

    this.attendeeInput.nativeElement.value = '';
    this.attendeeCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allAttendees.filter(attendee => attendee.toLowerCase().includes(filterValue));
  }

  addAttachment(event: any) {
    this.uploadAttachment(event.target.files[0])
      .catch(error => console.log(error))
  }

  removeAttachment(attachment: any) {
    this.attachments = this.attachments.filter(x => attachment !== x);
  }

  downloadAttachment(attachment: Attachment) {
    this.attachmentsService.downloadAttachment(attachment);
  }

  async uploadAttachment(file: File) {
    const buffer: ArrayBuffer = await file.arrayBuffer();
    this.attachments.push({
      content: new Uint8Array(buffer).toString(),
      name: file.name,
      type: file.type
    });
  }

  async submit() {
    // {
    //   "name":"Updated Event",
    //   "location":"testtesttes",
    //   "description":"A lot of bugs...",
    //   "startDateTime":"2021-08-10T09:40:23.097Z",
    //   "endDateTime":"2021-08-10T21:00:00.000Z",
    //   "attendees": [
    //   {
    //     "email": "bddinara@gmail.com"
    //   },
    //   {
    //     "email": "test3@gmail.com"
    //   }
    // ],
    //   "eventCategory": {
    //   "id": 3,
    //     "name": "Something"
    // }
    // }
    // const event: EventModel = {
    //   // ...this.initialEvent,
    //   ...this.event.getRawValue(),
    //   attendees: this.attendees.map(email => ({ email })),
    //   attachments: this.attachments,
    //   eventCategory: this.categories[this.event.getRawValue().category]
    // };


    const event: any = {
      name: this.event.getRawValue().name,
      location: this.event.getRawValue().location,
      description: this.event.getRawValue().description,
      startDateTime: this.event.getRawValue().startDateTime,
      endDateTime: this.event.getRawValue().endDateTime,
      attendees: this.attendees.map(email => ({ email })),
      eventCategory: this.categories[this.event.getRawValue().category],
      attachments: this.attachments,
    }

    this.eventsService.changeEvent(event, this.initialEvent.id).toPromise()
      .then(() => this.router.navigate(["/my-events"]))
      .catch(x => console.log(x))
  }


  validateEmail(email: string) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

}
