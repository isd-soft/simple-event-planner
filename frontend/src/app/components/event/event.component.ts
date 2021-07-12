import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
})
export class EventComponent implements AfterViewInit {
  displayedColumns: string[] = [
    'name',
    'location',
    'category',
    'description',
    'startDateTime',
    'endDateTime',
    'button',
  ];
  dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  onSearchClear() {
    this.searchKey = ' ';
    this.applyFilter();
  }
  applyFilter() {
    this.dataSource.filter = this.searchKey.trim().toLowerCase();
  }
}

export interface Element {
  name: string;
  location: string;
  category: string;
  description: string;
  startDateTime: string;
  endDateTime: string;
  button: string;
}

const ELEMENT_DATA: Element[] = [
  {
    name: 'Virtual Ceramic Connect',
    location: 'Strada Bulgară 33/1,',
    category: 'Conferences',
    description: 'very nice',
    startDateTime: '2021-09-01',
    endDateTime: '2021-09-21',
    button: '',
  },
  {
    name: 'Behavioral Decision Making',
    location: 'Strada Bulgară 33/1,',
    category: 'Seminars',
    description: 'Hdfvj',
    startDateTime: '2021-11-17',
    endDateTime: '2021-11-26',
    button: '',
  },
  {
    name: 'Developing Cultural Intelligence',
    location: 'Strada Bulgară 33/1,',
    category: 'Team Building Events',
    description: 'Hbdv',
    startDateTime: '2021-07-13',
    endDateTime: '2021-07-20',
    button: '',
  },
  {
    name: 'Ethical Leadership',
    location: 'Strada Stefan cel Mare 33/1,',
    category: 'Trade Shows/Expos',
    description: 'Hdfjg',
    startDateTime: '2021-07-21',
    endDateTime: '2021-10-22',
    button: '',
  },
  {
    name: 'Leadership Coaching Strategies',
    location: 'Strada Bulgară 33/1,',
    category: 'Executive Retreats',
    description: '',
    startDateTime: '2021-11-29',
    endDateTime: '2021-11-18',
    button: '',
  },
  {
    name: 'Leading Through Digital Disruption',
    location: 'Strada Bulgară 33/1,',
    category: 'Business',
    description: '',
    startDateTime: '2021-12-24',
    endDateTime: '2021-11-29',
    button: '',
  },
  {
    name: 'Managing Yourself and Leading Others',
    location: 'Strada Bulgară 33/1,',
    category: 'Corporate Golf Days',
    description: 'Hhfkh',
    startDateTime: '2021-11-08',
    endDateTime: '2021-07-19',
    button: '',
  },
  {
    name: 'Strategic Project Management',
    location: 'Strada Bulgară 33/1,',
    category: 'Product Launches',
    description: 'Hhfkh',
    startDateTime: '2021-09-27',
    endDateTime: '2021-10-07',
    button: '',
  },
  {
    name: 'The Authentic Leader',
    location: 'Strada Bulgară 33/1,',
    category: 'Shareholder',
    description: 'Hhfkh',
    startDateTime: '2021-12-22',
    endDateTime: '2021-11-08',
    button: '',
  },
  {
    name: 'Foundations of Everyday Leadership',
    location: 'Strada Bulgară 33/1,',
    category: 'Corporate Board Meetings',
    description: 'Hhfkh',
    startDateTime: '2021-11-30',
    endDateTime: '2021-12-01',
    button: '',
  },
  {
    name: 'Designing the Organization',
    location: 'Strada Bulgară 33/1,',
    category: 'Year End Functions',
    description: 'Hhfkh',
    startDateTime: '2021-09-29',
    endDateTime: '2021-08-20',
    button: '',
  },
];
