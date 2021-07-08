import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
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
export class HomePageComponent implements OnInit, AfterViewInit {
  @ViewChild('carousel')
  carousel: MatCarouselComponent;

  slides = [
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2020/04/Learn-the-fundamental-secrets-of-Java-programming-1-1024x684.jpg',
    },
    {
      image:
        'https://grubstreetauthor.co.uk/wp-content/uploads/2020/02/london-business-meeting-in-progress.jpg',
    },
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2020/04/The-WHO-and-WHY-of-the-JAVA-Internship-programs-2-768x478.png',
    },
    {
      image:
        'https://isd-soft.com/wp-content/uploads/2019/09/P.2.1-1768x995.jpg',
    },
    {
      image: 'https://isd-soft.com/wp-content/uploads/2019/09/P.3.3.jpg',
    },
  ];

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit() {
    // this.carousel.interval = 5000;
    // this.carousel.proportion = 25;
    // this.carousel.slides = 5;
  }
}
