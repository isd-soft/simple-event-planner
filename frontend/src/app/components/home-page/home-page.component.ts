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
        'https://writersrumpus.files.wordpress.com/2017/04/moon-with-people.jpg',
    },
    { image: 'https://images4.alphacoders.com/598/59866.jpg' },
    {
      image:
        'https://www.planetware.com/wpimages/2020/01/india-in-pictures-beautiful-places-to-photograph-taj-mahal.jpg',
    },
    {
      image:
        'https://www.reutersagency.com/wp-content/uploads/2021/02/RTX8M0UW.jpg',
    },
    {
      image:
        'https://static.independent.co.uk/2021/06/12/10/Pictures_of_the_Week-Global-Photo_Gallery_47292.jpg?width=1200&auto=webp&quality=75',
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
