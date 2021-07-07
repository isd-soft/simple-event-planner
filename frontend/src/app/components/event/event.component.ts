import { Component, OnInit } from '@angular/core';
export interface Element {
  name: string;
  location: string;
  category: string;
  description: string;
  startDateTime: string;
  endDateTime: string;
  test: string;
}

const ELEMENT_DATA: Element[] = [
  {
    name: 'Event1',
    location: 'dsadsad',
    category: 'dsadsad',
    description: 'dasdsad ',
    startDateTime: 'dsadsad',
    endDateTime: 'dasdasda',
    test: '',
  },
  {
    name: 'Event2',
    location: 'dasdsa',
    category: 'dasdada',
    description: ' dasdsad',
    startDateTime: 'dasda',
    endDateTime: 'dsadad',
    test: '',
  },
  {
    name: 'Event3',
    location: 'cevamndje',
    category: 'sebdej',
    description: 'dbejbdj ',
    startDateTime: 'ndhdb',
    endDateTime: 'sbcjsbcj',
    test: 'sdbjbd',
  },
];

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
})
export class EventComponent implements OnInit {
  displayedColumns: string[] = [
    'name',
    'location',
    'category',
    'description',
    'startDateTime',
    'endDateTime',
    'test',
  ];
  dataSource = ELEMENT_DATA;
  constructor() {}

  ngOnInit(): void {}
}
