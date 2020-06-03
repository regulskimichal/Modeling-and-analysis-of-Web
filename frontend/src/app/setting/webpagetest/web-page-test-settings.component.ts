import {Component, OnInit} from '@angular/core';
import {SettingService} from '../setting.service';
import {WebPageTestSetting} from '../web-page-test-setting';

@Component({
  selector: 'app-web-page-test-settings',
  templateUrl: './web-page-test-settings.component.html',
  styleUrls: ['./web-page-test-settings.component.scss']
})
export class WebPageTestSettingsComponent implements OnInit {

  displayedColumns = ['id', 'apiKeyId', 'pageUrl', 'cronExpression', 'zoneId', 'location', 'browser', 'connectivityProfile'];
  dataSource: WebPageTestSetting[] = [];

  constructor(private settingService: SettingService) {
  }

  async loadData() {
    const allSettings = await this.settingService.getAllSettings();
    this.dataSource = allSettings
      .filter(element => (element instanceof WebPageTestSetting))
      .map(element => element as WebPageTestSetting);
  }

  async ngOnInit() {
    await this.loadData();
  }

}
