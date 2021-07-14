import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { EventsService } from "../../services/events.service";
import { EventModel } from "../../models/event.model";

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
    'button'
  ];
  dataSource = new MatTableDataSource<EventModel>([]);

  constructor(private eventsService: EventsService) {
    eventsService.getMyEvents().toPromise()
      .then(eventModels => this.dataSource = new MatTableDataSource(eventModels))
      .catch(e => console.log(e))
  }

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

  convertDate(x: number[]) {
    // @ts-ignore
    return new Date(...x).toUTCString();
  }
}
