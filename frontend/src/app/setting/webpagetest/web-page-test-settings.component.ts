import { Component, OnInit } from '@angular/core';
import { SettingService } from '../setting.service';
import { WebPageTestSetting } from '../web-page-test-setting';
import { ApiType } from '../../api/api-type';

@Component({
  selector: 'app-web-page-test-settings',
  templateUrl: './web-page-test-settings.component.html',
  styleUrls: ['./web-page-test-settings.component.scss']
})
export class WebPageTestSettingsComponent implements OnInit {

  displayedColumns = ['id', 'apiKeyId', 'pageUrl', 'cronExpression', 'zoneId', 'location', 'browser', 'connectivityProfile'];
  dataSource: WebPageTestSetting[] = [];

  constructor(private settingService: SettingService) {}

  async ngOnInit() {
    await this.loadData();
  }

  async loadData() {
    this.dataSource = await this.settingService.getAllSettings(ApiType.WEB_PAGE_TEST) as WebPageTestSetting[];
  }

}
