import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {MatSort, Sort} from '@angular/material/sort';
import { EventsService } from "../../services/events.service";
import { EventModel } from "../../models/event.model";
import {Router} from "@angular/router";
import {EVENTS_URL} from "../../urls.config";

/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-event',
  templateUrl: './myevent.component.html',
  styleUrls: ['./myevent.component.css'],
})
export class MyeventComponent implements AfterViewInit {
  displayedColumns: string[] = [
    'name',
    'location',
    'category',
    'description',
    'startDateTime',
    'endDateTime',
    'isApproved',
  ];
  dataSource = new MatTableDataSource<EventModel>();
  data: any  = []

  constructor(private eventsService: EventsService,
              private router: Router) {
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;

  ngAfterViewInit() {
    this.dataSource.data = this.data;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.eventsService.getMyEvents().toPromise()
      .then(eventModels => {
        this.dataSource.data = eventModels;
        this.data = eventModels;
      })
      .catch(e => console.log(e))
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
