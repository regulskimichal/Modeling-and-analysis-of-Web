import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {ApiService} from '../api/api.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {title: 'Card 1', cols: 1, rows: 1},
          {title: 'Card 2', cols: 1, rows: 1},
        ];
      }

      return [
        {title: 'Card 1', cols: 2, rows: 1},
        {title: 'Card 2', cols: 2, rows: 1},
      ];
    })
  );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private apiService: ApiService
  ) {
  }


  async ngOnInit() {
    const apis = await this.apiService.getApis();
    console.log(apis);
    await this.apiService.updateApiKey(1, {apiKey: 'A.2f62fd1c8064745aba58e3a70b976121'});
  }
}
