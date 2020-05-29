import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {ApiService} from '../api/api.service';
import {WebPageTest} from "../webpagetest";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  private ELEMENT_DATA: WebPageTest[] = [
    {id: 1, apiKey:'2', url:'Hydrogen', cronExpression:'1.0079', location:'H',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Helium', cronExpression:'4.0026', location:'He',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Lithium', cronExpression:'6.941', location:'Li',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Beryllium', cronExpression:'9.0122', location:'Be',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Boron', cronExpression:'10.811', location:'B',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Carbon', cronExpression:'12.0107', location:'C',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Nitrogen', cronExpression:'14.0067', location:'N',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Oxygen', cronExpression:'15.9994', location:'O',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Fluorine', cronExpression:'18.9984', location:'F',userAgent:'1.0079'},
    {id: 1, apiKey:'2', url:'Neon', cronExpression:'20.1797', location:'Ne',userAgent:'1.0079'},
  ];

  displayedColumns: string[] = ['id', 'api_key', 'url', 'cron_expression', 'location','user_agent'];
  dataSource = this.ELEMENT_DATA;


  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {id:1, title: 'Web Page Test', cols: 1, rows: 1},
          {id:2, title: 'Schedule Settings', cols: 1, rows: 1},
        ];
      }

      return [
        {title: 'Web Page Test', cols: 1, rows: 1},
        {title: 'Schedule Settings', cols: 1, rows: 1},
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
