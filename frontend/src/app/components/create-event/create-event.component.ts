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
import { Router } from "@angular/router";
import {UserShortModel} from "../../models/user-short.model";
import {EventModel} from "../../models/event.model";
import {AttachmentsService} from "../../services/attachments.service";
import {Attachment} from "../../models/attachment.model";

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})

export class CreateEventComponent implements OnInit {
  readonly ATTENDEE_SEPARATOR: number[] = [ENTER, COMMA];
  readonly COVER_PHOTO_NAME: string = "cover_photo.jpg";


  event = new FormGroup({
    name: new FormControl('', [Validators.required]),
    location: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    imageSrc: new FormControl(),
    startDateTime: new FormControl(new Date(), [Validators.required]),
    endDateTime: new FormControl(new Date(), [Validators.required])
  });

  selectedCustomImg: Attachment | null = null;
  selectedImgElement: HTMLElement | null;

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
              private router: Router,
              private attachmentsService: AttachmentsService) {
    this.filteredAttendees = this.attendeeCtrl.valueChanges.pipe(
      startWith(null),
      map((attendee: string | null) => attendee ? this._filter(attendee) : this.allAttendees.slice()));

    eventCategoriesService.getAllCategories().toPromise()
      .then(categories => this.categories = categories)
      .catch(err => console.log(err));

    usersService.getAllUsers().toPromise()
      .then((users: UserShortModel[]) => this.allAttendees = users.map(user => user.email))
      .catch((err: any) => console.log(err))
  }

  ngOnInit(): void {
    this.selectedImgElement = document.getElementById("custom-image");
  }

  changeImage(s : string) {
    this.event.patchValue({ imageSrc: s })
    this.selectedCustomImg = null;
  }

  async csvInputChange(fileInputEvent: any) {
    this.event.patchValue({imageSrc: ''})
    this.selectedCustomImg = await this.attachmentsService.fileToAttachment(fileInputEvent.target.files[0]);

    if (this.selectedCustomImg && this.selectedImgElement) {
      this.attachmentsService.setImageFromAttachment(this.selectedCustomImg, this.selectedImgElement)
        .catch(error => console.log(error));
    }
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

  addAttachment(attachmentEvent: any) {
    this.attachmentsService.fileToAttachment(attachmentEvent.target.files[0])
      .then(attachment => this.attachments.push(attachment));
  }

  removeAttachment(attachment: any) {
    this.attachments = this.attachments.filter(x => attachment !== x);
  }

  validateEmail(email: string) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  async submit() {
    const event: any = this.event.getRawValue();

    event.eventCategory = this.categories[event.category];
    event.attendees = this.attendees.map(email => ({ email: email }));
    event.attachments = [ ...this.attachments ];

    event.startDateTime = event.startDateTime.toISOString();
    event.endDateTime = event.endDateTime.toISOString();

    if (event.imageSrc) {
      this.selectedCustomImg = await fetch(event.imageSrc)
        .then(res => res.blob())
        .then(blob => new File([blob], this.COVER_PHOTO_NAME))
        .then(file => this.attachmentsService.fileToAttachment(file));
    }
    if (this.selectedCustomImg) {
      event.attachments.push(this.selectedCustomImg);
    }

    this.eventsService.createEvent(event).toPromise()
      .then(() => this.router.navigate(["/my-events"]))
      .catch(error => console.log(error));
  }

}
