import { Component, OnInit } from '@angular/core';
import { SettingService } from '../setting.service';
import { PageSpeedSetting } from '../page-speed-setting';
import { ApiType } from '../../api/api-type';

@Component({
  selector: 'app-page-speed-settings',
  templateUrl: './page-speed-settings.component.html',
  styleUrls: ['./page-speed-settings.component.scss']
})
export class PageSpeedSettingsComponent implements OnInit {

  displayedColumns = ['id', 'apiKeyId', 'pageUrl', 'cronExpression', 'zoneId', 'strategy'];
  dataSource: PageSpeedSetting[] = [];

  constructor(private settingService: SettingService) {}

  async ngOnInit() {
    await this.loadData();
  }

  async loadData() {
    this.dataSource = await this.settingService.getAllSettings(ApiType.PAGE_SPEED) as PageSpeedSetting[];
  }

}
