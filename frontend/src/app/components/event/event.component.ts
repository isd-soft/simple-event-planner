import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
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
    name: 'Event1',
    location: 'H',
    category: 'H',
    description: 'Hdfb',
    startDateTime: 'ndkhkh ',
    endDateTime: 'bdssndkenfbd',
    button: 'sdnkdkd',
  },
  {
    name: 'Event2',
    location: 'H',
    category: 'H',
    description: 'Hdfvj',
    startDateTime: 'sbdjekdfjejfhjdjfbdjbfjd',
    endDateTime: 'sfkfkek',
    button: 'dnknvk',
  },
  {
    name: 'Event3',
    location: 'H',
    category: 'H',
    description: 'Hbdv',
    startDateTime: 'dbfdbk ',
    endDateTime: 'dnfefn',
    button: 'slkfeogh',
  },
  {
    name: 'Event4',
    location: 'H',
    category: 'H',
    description: 'Hdfjg',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Event5',
    location: 'H',
    category: 'H',
    description: 'Hsjkh',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Event6',
    location: 'H',
    category: 'H',
    description: 'Hhfkh',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Event7',
    location: 'H',
    category: 'H',
    description: 'Hfkehf',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Event8',
    location: 'H',
    category: 'H',
    description: 'Hdfhehf',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Event9',
    location: 'H',
    category: 'H',
    description: 'Hkjdh',
    startDateTime: ' ',
    endDateTime: '',
    button: '',
  },
];
