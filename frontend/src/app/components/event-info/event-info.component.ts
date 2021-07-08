import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-event-info',
  templateUrl: './event-info.component.html',
  styleUrls: ['./event-info.component.css']
})
export class EventInfoComponent implements OnInit {

  constructor() {}

  event = {
    "name": "Party Hard",
    "location": "Moldova",
    "category": "Party",
    "description": "Super team building event. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    "imageSrc": "http://www.canal2.md/media/2018/03/party.jpg",
    "startDate": "2021-07-08T09:40:23.097Z",
    "endDate": "2021-07-08T21:00:00.000Z"
  }

  startDate = new Date(this.event.startDate);
  endDate = new Date(this.event.endDate);

  attendees = [
    "stanislav@isd.md",
    "dinara@mail.com",
    "marcel@mail.com",
    "denis@mail.com"
  ]

  ngOnInit(): void {
  }
}
