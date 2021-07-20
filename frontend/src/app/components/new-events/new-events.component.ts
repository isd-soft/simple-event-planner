import { OnInit } from '@angular/core';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import {EventModel} from "../../models/event.model";
import {EventsService} from "../../services/events.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-events',
  templateUrl: './new-events.component.html',
  styleUrls: ['./new-events.component.css'],
})
export class NewEventsComponent implements AfterViewInit {
  displayedColumns: string[] = [
    'name',
    'location',
    'category',
    'startDateTime',
    'endDateTime',
    'action'
  ];
  dataSource = new MatTableDataSource<EventModel>();
  data: any  = []

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  searchKey: string;

  filterType: string = "ALL";


  constructor(private eventsService: EventsService,
              private router: Router) {
  }

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

  approveEvent(event: Event, eventModel: EventModel) {
    this.eventsService.approveEvent(eventModel.id).toPromise()
      .then(() => eventModel.isApproved = true)
      .catch(error => console.log(error));

    event.stopPropagation();
  }

  declineEvent(event: Event, eventModel: EventModel) {
    this.eventsService.declineEvent(eventModel.id).toPromise()
      .then(() => {
        this.data = this.data.filter((event: EventModel) => event.id != eventModel.id);
        this.dataSource.data = this.data;
      })
      .catch(x => console.log(x));
    event.stopPropagation();
  }

  filterEvents(isApproved: boolean) {
    this.dataSource.data = this.data.filter((ev: EventModel) => ev.isApproved == isApproved);

    this.filterType = isApproved ? "APPROVED" : "PENDING";
  }
}
