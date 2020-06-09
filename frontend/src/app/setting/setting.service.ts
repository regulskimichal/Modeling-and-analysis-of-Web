import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { WebPageTestSetting } from './web-page-test-setting';
import { PageSpeedSetting } from './page-speed-setting';
import { WebPageTestMeasurement } from '../measurement/web-page-test-measurement';
import { PageSpeedMeasurement } from '../measurement/page-speed-measurement';
import { ApiType } from '../api/api-type';

@Injectable({
  providedIn: 'root'
})
export class SettingService {

  constructor(private http: HttpClient) {}

  async getSetting(id: number): Promise<WebPageTestSetting | PageSpeedSetting> {
    return this.http.get<WebPageTestSetting | PageSpeedSetting>(`/setting/${id}`).toPromise();
  }

  async getAllSettings(apiType: ApiType): Promise<WebPageTestSetting[] | PageSpeedSetting[]> {
    const params = new HttpParams().set('type', apiType);
    return this.http.get<WebPageTestSetting[] | PageSpeedSetting[]>('/setting', {params}).toPromise();
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
