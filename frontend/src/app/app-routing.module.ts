import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventComponent } from './components/event/event.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { MyeventComponent } from './components/myevent/myevent.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { EventInfoComponent } from './components/event-info/event-info.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'sign-up',
    component: SignupComponent,
  },
  {
    path: 'events',
    component: EventComponent,
  },
  {
    path: 'my-events',
    component: MyeventComponent,
  },
  {
    path: 'create-event',
    component: CreateEventComponent,
  },
  {
    path: 'calendar',
    component: CalendarComponent,
  },
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'event-info',
    component: EventInfoComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
