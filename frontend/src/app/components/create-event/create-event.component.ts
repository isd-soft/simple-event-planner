import { Component, NgModule, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {ElementRef, ViewChild} from '@angular/core';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {COMMA, ENTER} from '@angular/cdk/keycodes';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})

export class CreateEventComponent implements OnInit {

  ngOnInit(): void {
  }

  event = new FormGroup({
    name: new FormControl('', [Validators.required]),
    location: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    imageSrc: new FormControl('https://grubstreetauthor.co.uk/wp-content/uploads/2020/02/london-business-meeting-in-progress.jpg'),
    startDate: new FormControl(new Date(), [
      Validators.required
    ]),
    endDate: new FormControl(new Date(), [
      Validators.required
    ])
  });

  submit() {
    console.log(this.event.value);
    console.log(this.attendees);
  }

  // ==== Image ==== //

  // Default image
  changeImage(s : string) {
    this.event.patchValue({imageSrc: s})
  }

  // Custom image
  csvInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
  }

  // ==== Selector for attendees ==== //

  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  attendeeCtrl = new FormControl();
  filteredAttendees: Observable<string[]>;
  attendees: string[] = ['stanislav@isd.md'];
  allAttendees: string[] = ['marcel@mail.com', 'dinara@mail.com', 'andrei@mail.com', 'denis@mail.com', 'vlad@mail.com'];

  @ViewChild('attendeeInput')
  attendeeInput!: ElementRef<HTMLInputElement>;

  constructor() {
    this.filteredAttendees = this.attendeeCtrl.valueChanges.pipe(
        startWith(null),
        map((attendee: string | null) => attendee ? this._filter(attendee) : this.allAttendees.slice()));
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our attendee
    if (value) {
      this.attendees.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.attendeeCtrl.setValue(null);
  }

  remove(attendee: string): void {
    const index = this.attendees.indexOf(attendee);

    if (index >= 0) {
      this.attendees.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.attendees.push(event.option.viewValue);
    this.attendeeInput.nativeElement.value = '';
    this.attendeeCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allAttendees.filter(attendee => attendee.toLowerCase().includes(filterValue));
  }

}