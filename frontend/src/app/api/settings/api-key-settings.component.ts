import { Component, OnInit, ViewChild } from '@angular/core';
import { ApiService } from '../api.service';
import { ApiKey } from '../api-key';
import { MatTable } from '@angular/material/table';
import { ApiType } from '../api-type';

@Component({
  selector: 'app-api-key-settings',
  templateUrl: './api-key-settings.component.html',
  styleUrls: ['./api-key-settings.component.scss']
})
export class ApiKeySettingsComponent implements OnInit {

  constructor(private apiService: ApiService) {}
  @ViewChild('table') table: MatTable<Element>;
  displayedColumns: string[] = ['id', 'name', 'apiKey', 'defaultKey', 'type'];
  dataSource = [];

  ELEMENT_DATA: [
    {id: 1, name: 'Hydrogen', apiKey: `1.0079`, defaultKey: true, type: ApiType},
  ];
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase();

  }
  addRow() {
    let data: ApiKey[] = [];
    if (this.dataSource) {
      data = (this.dataSource as ApiKey[]);
    }
    data.push((this.ELEMENT_DATA)[data.length]);
    this.dataSource = data;
    this.table.renderRows();
  }
  async loadData() {
    this.dataSource = await this.apiService.getAllApiKeys();
  }

  async ngOnInit() {
    await this.loadData();
  }



}
