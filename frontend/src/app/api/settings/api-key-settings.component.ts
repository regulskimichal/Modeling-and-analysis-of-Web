import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api.service';

@Component({
  selector: 'app-api-key-settings',
  templateUrl: './api-key-settings.component.html',
  styleUrls: ['./api-key-settings.component.scss']
})
export class ApiKeySettingsComponent implements OnInit {

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

}
