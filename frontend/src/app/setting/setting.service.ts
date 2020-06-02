import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiKey } from '../api/apiKey';
import { WebPageTestSetting } from './webPageTestSetting';
import { PageSpeedSetting } from './pageSpeedSetting';
import { WebPageTestMeasurement } from '../measurement/webPageTestMeasurement';
import { PageSpeedMeasurement } from '../measurement/pageSpeedMeasurement';

@Injectable({
  providedIn: 'root'
})
export class SettingService {
  constructor(private http: HttpClient) {}

  async getSetting(id: number): Promise<WebPageTestSetting | PageSpeedSetting> {
    return this.http.get<WebPageTestSetting | PageSpeedSetting>(`/setting/${id}`).toPromise();
  }

  async getAllSettings(): Promise<Array<WebPageTestSetting | PageSpeedSetting>> {
    return this.http.get<Array<WebPageTestSetting | PageSpeedSetting>>('/setting').toPromise();
  }

  async setEnabled(id: number): Promise<void> {
    return this.http.post<void>(`/setting/${id}/enable`, {}).toPromise();
  }

  async setDisabled(id: number): Promise<void> {
    return this.http.post<void>(`/setting/${id}/disable`, {}).toPromise();
  }

  async getMeasurements(settingId: number): Promise<Array<WebPageTestMeasurement | PageSpeedMeasurement>> {
    return this.http.get<Array<WebPageTestMeasurement | PageSpeedMeasurement>>(`/setting/${settingId}/measurements`).toPromise();
  }

  async saveSetting(id: number, settingDto: WebPageTestSetting | PageSpeedSetting): Promise<WebPageTestSetting | PageSpeedSetting> {
    return this.http.post<WebPageTestSetting | PageSpeedSetting>(`/setting`, settingDto).toPromise();
  }

  async deleteSetting(id: number): Promise<void> {
    return this.http.delete<void>(`/setting/${id}`).toPromise();
  }

  async deleteAllSettings(id: number): Promise<void> {
    return this.http.delete<void>(`/setting`).toPromise();
  }

}
