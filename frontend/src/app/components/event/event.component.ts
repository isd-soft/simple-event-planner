import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {EventService} from 'src/app/services/event.service';
import {Event} from '../../models/event';
import {Router} from '@angular/router';
import {EventModel} from "../../models/event.model";


@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],

})
export class EventComponent implements AfterViewInit {
  event: Event[] = [];


  constructor(private eventService: EventService,
              private router: Router) { }

  displayedColumns: string[] = [
    'name',
    'location',
    'category',
    'description',
    'startDateTime',
    'endDateTime'
  ];

  dataSource = new MatTableDataSource<Event>();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;

  ngAfterViewInit() {
    this.eventService.getEvents().subscribe((event) => {
      this.dataSource.data = event;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  onSearchClear() {
    this.searchKey = ' ';
    this.applyFilter();
  }

  applyFilter() {
    this.dataSource.filter = this.searchKey.trim().toLowerCase();
  }

  convert(x: any) {
    return new Date(x).toLocaleString();
  }

  selectEventRow(event: EventModel) {
    this.router.navigate(["/events/", event.id])
  }
}
