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
  events: EventModel[] = [];

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

    this.eventsService.getApprovedEvents().toPromise().then((events) => {
      this.events = events;
      console.log(events);
    });

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
    this.attachmentsService.fileToAttachment(event.target.files[0])
      .then(attachment => this.attachments.push(attachment));
  }

  removeAttachment(attachment: any) {
    this.attachments = this.attachments.filter(x => attachment !== x);
  }

  downloadAttachment(attachment: Attachment) {
    this.attachmentsService.downloadAttachment(attachment)
      .catch(console.log);
  }

  async submit() {
    let event: any = this.event.getRawValue();

    event.eventCategory = this.categories[event.category];
    event.attendees = this.attendees.map(email => ({ email: email }));
    event.attachments = [ ...this.attachments, this.coverImage ];

    // this.selectedCustomImg = await fetch(event.imageSrc)
    //      .then(res => res.blob())
    //     .then(blob => new File([blob], this.COVER_PHOTO_NAME))
    //     .then(file => this.attachmentsService.fileToAttachment(file));

    event.startDateTime = new Date(event.startDateTime).toISOString()
    event.endDateTime = new Date(event.endDateTime).toISOString()

    console.log(event);

    this.eventsService.changeEvent(event, this.initialEvent.id).toPromise()
      .then(() => this.router.navigate(["/my-events"]))
      .catch(error => console.log(error));
  }


  validateEmail(email: string) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  filterByApprovedEvents = (d: Date | null): boolean => {
    const date = (d || new Date());
    let startDate;
    let endDate;
    for (let i = 0; i < this.events.length; i++) {
      startDate = new Date(this.events[i].startDateTime);

      endDate = new Date(this.events[i].endDateTime);
      if (startDate <= date && date <= endDate) {
        return false;
      }
    }
    return true;
  }
}
