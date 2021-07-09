import { ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { Component } from '@angular/core';
import { ChartType, ChartDataSets } from 'chart.js';
import { MultiDataSet } from 'ng2-charts';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css'],
})
export class AnalyticsComponent {
  lineChartData: ChartDataSets[] = [
    {
      data: [15, 12, 18, 15, 7, 15],
      label: 'Total amount of organized events/month',
    },
  ];

  lineChartLabels: Label[] = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
  ];

  lineChartOptions = {
    responsive: true,
  };

  lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(255,255,0,0.28)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';

  barChartOptions: ChartOptions = {
    responsive: true,
  };
  barChartLabels: Label[] = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
  ];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: [45, 37, 60, 70, 46, 33], label: 'Nr of attendees/month' },
  ];
  doughnutChartLabels: Label[] = ['Rejected', 'Accepted', 'Total/month'];
  doughnutChartData: MultiDataSet = [[20, 35, 55]];
  doughnutChartType: ChartType = 'doughnut';
}
