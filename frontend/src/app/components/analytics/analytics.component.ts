import { Label, ThemeService } from 'ng2-charts';
import { Component } from '@angular/core';
import { ChartType, ChartDataSets } from 'chart.js';
import { MultiDataSet } from 'ng2-charts';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { EventCategory } from 'src/app/models/event-category.model';
import { Statistics } from 'src/app/models/statistics';
import { StatisticsService } from 'src/app/services/statistics.service';
import { EventCategoriesService } from 'src/app/services/event-categories.service';
import { Health } from 'src/app/models/Health.model';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css'],
})
export class AnalyticsComponent {
  stats: Statistics = {
    approvedEvents: 0,
    unapprovedEvents: 0,
    numberOfUsers: 0,
    totalEvents: 0,
  };
  health: any;
  app: any;
  constructor(
    private statisticsService: StatisticsService,
    private categoryService: EventCategoriesService
  ) {}

  doughnutChartLabels: Label[] = ['Unapproved', 'Approved', 'Total'];
  doughnutChartData: MultiDataSet = [
    [
      this.stats.unapprovedEvents,
      this.stats.approvedEvents,
      this.stats.totalEvents,
    ],
  ];
  doughnutChartType: ChartType = 'doughnut';

  selectable = false;
  removable = false;
  addOnBlur = true;

  // Categories

  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  categories: EventCategory[] = [];

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      this.categoryService
        .postCategory({ name: value })
        .toPromise()
        .then()
        .catch((error) => {
          console.log(error);
        });
      this.categories.push({ name: value });
    }

    event.chipInput!.clear();
  }

  remove(category: EventCategory): void {
    const index = this.categories.indexOf(category);

    if (index >= 0) {
      this.categories.splice(index, 1);
    }
  }

  ngOnInit(): void {
    this.statisticsService.getStatistics().subscribe((stats) => {
      this.stats = stats;
      this.doughnutChartData = [
        [stats.unapprovedEvents, stats.approvedEvents, stats.totalEvents],
      ];
    });
    this.statisticsService.getHealth().subscribe((health) => {
      this.health = health;
      console.log(this.health.status);
    });
    this.statisticsService.getAppInfo().subscribe((app: any) => {
      this.app = app;
      console.log(app.app.name);
    });
    this.categoryService.getAllCategories().subscribe((categories) => {
      this.categories = categories;
    });
  }
}
