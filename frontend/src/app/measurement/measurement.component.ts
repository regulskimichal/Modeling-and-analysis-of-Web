import { Component, Inject, OnInit } from '@angular/core';
import { SettingService } from '../setting/setting.service';
import { ActivatedRoute } from '@angular/router';
import { WebPageTestMeasurement } from './web-page-test-measurement';
import { PageSpeedMeasurement } from './page-speed-measurement';
import { BACKEND_URL } from '../backend.token';

@Component({
  selector: 'app-measurement',
  templateUrl: './measurement.component.html',
  styleUrls: ['./measurement.component.scss']
})
export class MeasurementComponent implements OnInit {

  private id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
  exportUrl = `${this.backendUrl}/setting/${this.id}/measurements/export`;

  displayedColumns = ['id', 'apiKeyId', 'pageUrl', 'cronExpression', 'zoneId', 'location', 'browser', 'connectivityProfile'];
  dataSource: Array<PageSpeedMeasurement | WebPageTestMeasurement> = [];

  constructor(
    private settingService: SettingService,
    private route: ActivatedRoute,
    @Inject(BACKEND_URL) public backendUrl: string,
  ) {}

  async ngOnInit() {
    await this.loadData();
  }

  async loadData() {
    this.dataSource = await this.settingService.getMeasurements(this.id);
  }

}
