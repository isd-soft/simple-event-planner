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
    name: 'Event1',
    location: 'H',
    category: 'H',
    description: 'Hdfb',
    startDateTime: 'ajbsjk',
    endDateTime: 'bdssndkenfbd',
    button: 'sdnkdkd',
  },
  {
    name: 'sjdbj2',
    location: 'H',
    category: 'H',
    description: 'Hdfvj',
    startDateTime: 'ahgsj',
    endDateTime: 'sfkfkek',
    button: 'dnknvk',
  },
  {
    name: 'vvjshdent3',
    location: 'H',
    category: 'H',
    description: 'Hbdv',
    startDateTime: 'sbdejkb',
    endDateTime: 'dnfefn',
    button: 'slkfeogh',
  },
  {
    name: 'djdbnt4',
    location: 'H',
    category: 'H',
    description: 'Hdfjg',
    startDateTime: 'wdbjwb',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Lkent5',
    location: 'H',
    category: 'H',
    description: 'Hsjkh',
    startDateTime: 'sbnjwbk',
    endDateTime: '',
    button: '',
  },
  {
    name: 'Tnent6',
    location: 'H',
    category: 'H',
    description: 'Hhfkh',
    startDateTime: 'nnshdvh',
    endDateTime: '',
    button: '',
  },
];
