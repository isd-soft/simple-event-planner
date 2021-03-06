import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { NavigationComponent } from './components/navigation/navigation.component';
import { LoginComponent } from './components/login/login.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { SignupComponent } from './components/signup/signup.component';
import { EventComponent } from './components/event/event.component';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { FormsModule } from '@angular/forms';
import { MyeventComponent } from './components/myevent/myevent.component';
//import { CalendarComponent } from './components/calendar/calendar.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import {
  ScheduleModule,
  RecurrenceEditorModule,
  DayService,
  WeekService,
  WorkWeekService,
  MonthService,
  MonthAgendaService,
  AgendaService,
  TimelineMonthService,
  TimelineViewsService,
} from '@syncfusion/ej2-angular-schedule';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { MatCarouselModule } from '@ngmodule/material-carousel';
import { NewEventsComponent } from './components/new-events/new-events.component';
import { AnalyticsComponent } from './components/analytics/analytics.component';
import { ChartsModule } from 'ng2-charts';
import { EventInfoComponent } from './components/event-info/event-info.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogComponent } from './components/dialog/dialog.component';
import { TokenInterceptor } from './interceptors/token.interceptor';
//import { EventOverviewComponent } from './components/event-overview/event-overview.component';

import {
  NgxMatDatetimePickerModule,
  NgxMatTimepickerModule,
  NgxMatNativeDateModule,
} from '@angular-material-components/datetime-picker';
import { UpdateEventComponent } from './components/update-event/update-event.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { CommentListComponent } from './components/util/comment-list/comment-list.component';
import { CommentComponent } from './components/util/comment/comment.component';
import { ReactionsComponent } from './components/util/reactions/reactions.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    LoginComponent,
    SignupComponent,
    EventComponent,
    MyeventComponent,
    CreateEventComponent,
    CalendarComponent,
    HomePageComponent,
    NewEventsComponent,
    AnalyticsComponent,
    EventInfoComponent,
    DialogComponent,
    UpdateEventComponent,
    CommentListComponent,
    CommentComponent,
    ReactionsComponent,
    // EventOverviewComponent,
  ],
  entryComponents: [DialogComponent],
  imports: [
    NgxMatTimepickerModule,
    NgxMatDatetimePickerModule,
    NgxMatNativeDateModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatMenuModule,
    MatTableModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatChipsModule,
    MatAutocompleteModule,
    ScheduleModule,
    RecurrenceEditorModule,
    MatCarouselModule.forRoot(),
    ChartsModule,
    MatDialogModule,
  ],
  providers: [
    DayService,
    WeekService,
    WorkWeekService,
    MonthService,
    AgendaService,
    MonthAgendaService,
    TimelineViewsService,
    TimelineMonthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],

  bootstrap: [AppComponent],
})
export class AppModule {}
