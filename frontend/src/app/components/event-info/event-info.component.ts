import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';
import {EventService} from 'src/app/services/event.service';
import {ActivatedRoute} from '@angular/router';
import {Event} from 'src/app/models/event';
import {Router} from '@angular/router';

@Component({
  selector: 'app-event-info',
  templateUrl: './event-info.component.html',
  styleUrls: ['./event-info.component.css'],
})
export class EventInfoComponent implements OnInit {
  attendeeEmails: string[] = [];
  event: Event;

  constructor(public dialog: MatDialog, private eventService: EventService, private route: ActivatedRoute, private router: Router) {
  }

  redirection() {
    window.location.href = "/events"
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

  eventMock = {
    name: 'Party Hard',
    location: 'Moldova',
    category: 'Party',
    description:
      'Super team building event. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    imageSrc: 'http://www.canal2.md/media/2018/03/party.jpg',
    startDate: '2021-07-08T09:40:23.097Z',
    endDate: '2021-07-08T21:00:00.000Z',
  };


  attendeesMock = [
    'stanislav@isd.md',
    'dinara@mail.com',
    'marcel@mail.com',
    'denis@mail.com',
  ];

  ngOnInit(): void {
    let stringId = this.route.snapshot.paramMap.get('id');
    if (stringId != null) {
      let id: number = parseInt(stringId);
      this.eventService.getEventById(id).subscribe((event) => {
        this.event = event
      });
    }
  }
}
