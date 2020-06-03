import {Component, OnInit} from '@angular/core';
import {SettingService} from '../setting.service';
import {PageSpeedSetting} from '../page-speed-setting';

@Component({
  selector: 'app-page-speed-settings',
  templateUrl: './page-speed-settings.component.html',
  styleUrls: ['./page-speed-settings.component.scss']
})
export class PageSpeedSettingsComponent implements OnInit {

  constructor(private settingService: SettingService) {
  }

  displayedColumns = ['id', 'apiKeyId', 'pageUrl', 'cronExpression', 'zoneId', 'strategy'];
  dataSource = [];

  async loadData() {
    const allSettings = await this.settingService.getAllSettings();
    this.dataSource = allSettings.filter(element => (element instanceof PageSpeedSetting));
  }

  async ngOnInit() {
    await this.loadData();
  }

}
