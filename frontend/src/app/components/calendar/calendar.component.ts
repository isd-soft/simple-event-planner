import { Component, OnInit } from '@angular/core';
import { EventSettingsModel } from '@syncfusion/ej2-angular-schedule';
import { AuthService } from 'src/app/services/auth.service';
import { EventsService } from 'src/app/services/events.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
})
export class CalendarComponent {
  public eventObject: EventSettingsModel = {
    fields: {
      location: { name: 'location' },
      description: { name: 'description' },
    },
  };
  constructor(private eventsService: EventsService) {
    eventsService
      .getApprovedEvents()
      .toPromise()
      .then(
        (events: any) =>
          (this.eventObject.dataSource = events.map((event: any) => ({
            Id: event.id,
            Subject: event.name,
            StartTime: new Date(event.startDateTime),
            EndTime: new Date(event.endDateTime),
            IsReadonly: true,
            ...event,
          })))
      )
      .catch(console.log);
  }
}
