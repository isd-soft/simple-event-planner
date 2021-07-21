import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { EventsService } from 'src/app/services/events.service';
import { EventModel } from '../../models/event.model';

import {
  MatCarousel,
  MatCarouselComponent,
  MatCarouselSlideComponent,
} from '@ngmodule/material-carousel';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css'],
})
export class HomePageComponent implements OnInit {
  @ViewChild('carousel')
  carousel: MatCarouselComponent;

  events: EventModel[];

  slides = [
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2019/09/P.2.1-1768x995.jpg',
    },
    {
      image: 'https://isd-soft.com/wp-content/uploads/2019/09/P.3.3.jpg',
    },
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2020/04/The-WHO-and-WHY-of-the-JAVA-Internship-programs-2-768x478.png',
    },
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2020/04/Learn-the-fundamental-secrets-of-Java-programming-1-1024x684.jpg',
    },
  ];

  constructor(private eventsService: EventsService) {}

  ngOnInit() {
    this.eventsService
      .getApprovedEvents()
      .toPromise()
      .then((events) => {
        this.events = events.sort((a, b) => {
          return (
            new Date(a.startDateTime).getTime() -
            new Date(b.startDateTime).getTime()
          );
        });

        if (this.events.length > 5) {
          this.events.length = 5;
        }

        console.log(this.events);
      })
      .catch((e) => console.log(e));
  }

  convertDate(x: any) {
    return new Date(x).toLocaleString();
  }

  getTime(x: any) {
    return new Date(x).toLocaleTimeString();
  }

  getDate(x: any) {
    return new Date(x).getDate();
  }

  getMonth(x: any) {
    return new Date(x).toLocaleString('default', { month: 'long' });
  }
}
