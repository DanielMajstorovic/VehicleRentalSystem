import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { NgChartsModule } from 'ng2-charts';
import { ChartConfiguration, ChartOptions } from 'chart.js';

interface IncomePerDayDto { day: number; income: number; }
interface IncomeByTypeDto { type: string; income: number; }
interface BreakdownDto { vehicleUid: string; count: number; }

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [CommonModule, FormsModule, NgChartsModule],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

  years = Array.from({ length: 6 }, (_, i) => new Date().getFullYear() - i);
  months = [
    { value: 1, label: '01 - Jan' }, { value: 2, label: '02 - Feb' },
    { value: 3, label: '03 - Mar' }, { value: 4, label: '04 - Apr' },
    { value: 5, label: '05 - May' }, { value: 6, label: '06 - Jun' },
    { value: 7, label: '07 - Jul' }, { value: 8, label: '08 - Aug' },
    { value: 9, label: '09 - Sep' }, { value: 10, label: '10 - Oct' },
    { value: 11, label: '11 - Nov' }, { value: 12, label: '12 - Dec' }
  ];

  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth() + 1;

  get monthControl(): string {
    return `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}`;
  }

  lineChartData: ChartConfiguration<'bar'>['data'] = { labels: [], datasets: [] };
  pieData: ChartConfiguration<'pie'>['data'] = { labels: [], datasets: [] };

  lineOptions: ChartOptions<'bar'> = { responsive: false };
  pieOptions: ChartOptions<'pie'> = { responsive: false };

  revenueByType: IncomeByTypeDto[] = [];
  breakdownCounts: BreakdownDto[] = [];

  private statsUrl = 'http://localhost:8080/statistics';

  constructor(private http: HttpClient, private toast: ToastrService) {}

  ngOnInit() { this.loadAll(); }

  onDateChange() { this.loadAll(); }

  loadAll() {
    const [yearStr, monthStr] = this.monthControl.split('-');
    const year = +yearStr;
    const month = +monthStr;

    const p1 = this.http.get<IncomePerDayDto[]>(`${this.statsUrl}/income-per-day`,
      { params: new HttpParams().set('year', year).set('month', month) });
    const p2 = this.http.get<IncomeByTypeDto[]>(`${this.statsUrl}/income-by-type`,
      { params: new HttpParams().set('year', year).set('month', month) });
    const p3 = this.http.get<BreakdownDto[]>(`${this.statsUrl}/breakdowns-count`);

    Promise.all([
      p1.toPromise().then(res => res ?? []),
      p2.toPromise().then(res => res ?? []),
      p3.toPromise().then(res => res ?? [])
    ]).then(([daily, byType, brk]) => {
      this.prepareLineChart(daily);
      this.preparePie(byType);
      this.revenueByType = byType;
      this.breakdownCounts = brk;
    }).catch(() => this.toast.error('Error loading statistics'));
  }

  private prepareLineChart(data: IncomePerDayDto[]) {
    const labels = data.map(d => d.day.toString());
    const values = data.map(d => d.income);
    const max = Math.max(...values);
    const min = Math.min(...values.filter(v => v > 0));
    const useLog = max / Math.max(min || 1, 1) > 50;

    this.lineChartData = {
      labels,
      datasets: [{ label: 'Revenue', data: values }]
    };

    const linearScale = {
      beginAtZero: true,
      ticks: { callback: (v: number | string) => Intl.NumberFormat('en-US').format(Number(v)) }
    };

    const logScale = {
      type: 'logarithmic' as const,
      min: 1,
      ticks: { callback: (v: number | string) => Intl.NumberFormat('en-US').format(Number(v)) }
    };

    this.lineOptions = {
      responsive: false,
      animation: false,
      plugins: { legend: { display: false } },
      scales: { x: {}, y: useLog ? logScale : linearScale }
    };
  }

  private preparePie(data: IncomeByTypeDto[]) {
    this.pieData = {
      labels: data.map(d => d.type),
      datasets: [{ data: data.map(d => d.income) }]
    };

    this.pieOptions = { responsive: false };
  }
}
