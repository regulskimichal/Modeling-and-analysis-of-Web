import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { ApiService } from '../api/api.service';
import { ApiType } from '../api/apiType';
import { WebPageTestSetting } from '../setting/webPageTestSetting';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  displayedColumns: string[] = ['id', 'api_key', 'url', 'cron_expression', 'location', 'user_agent'];
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {id: 1, title: 'Web Page Test', cols: 1, rows: 1},
          {id: 2, title: 'Schedule Settings', cols: 1, rows: 1},
        ];
      }

      return [
        {title: 'Web Page Test', cols: 1, rows: 1},
        {title: 'Schedule Settings', cols: 1, rows: 1},
      ];
    })
  );
  private ELEMENT_DATA: WebPageTestSetting[] = [
    {
      id: 1,
      apiKeyId: 2,
      pageUrl: 'Hydrogen',
      cronExpression: '1.0079',
      location: 'H',
      zoneId: 'Europe/Warsaw',
      browser: null,
      connectivityProfile: null
    },
    {
      id: 1,
      apiKeyId: 2,
      pageUrl: 'Helium',
      cronExpression: '4.0026',
      location: 'He',
      zoneId: 'Europe/Warsaw',
      browser: null,
      connectivityProfile: null
    },
  ];
  dataSource = this.ELEMENT_DATA;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private apiService: ApiService
  ) {}

  async ngOnInit() {
    const apis = await this.apiService.getAllApiKeys();
    console.log(apis);
    await this.apiService.updateApiKey(1, {
      name: 'New key',
      apiKey: 'A.2f62fd1c8064745aba58e3a70b976121',
      defaultKey: false,
      type: ApiType.WEB_PAGE_TEST
    });
  }

}
