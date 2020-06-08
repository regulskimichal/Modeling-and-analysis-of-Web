import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-api-key-settings',
  templateUrl: './api-key-settings.component.html',
  styleUrls: ['./api-key-settings.component.scss']
})
export class ApiKeySettingsComponent implements OnInit {
  private router: Router;

  constructor(private apiService: ApiService) {

  }

  displayedColumns: string[] = ['id', 'name', 'apiKey', 'defaultKey', 'type'];
  dataSource = [];

  async loadData() {
    this.dataSource = await this.apiService.getAllApiKeys();
  }

  async ngOnInit() {
    await this.loadData();
  }

  public highlightSelectedRow(row): void {
    this.router.navigate(['../', {id: 'id'}]);

  }

}
