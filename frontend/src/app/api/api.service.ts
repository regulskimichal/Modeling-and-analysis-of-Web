import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Api} from './api';
import {ApiKey} from './api-key';

@Injectable({
    providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {
  }

  async getApis(): Promise<Array<Api>> {
    return this.http.get<Array<Api>>('/api').toPromise();
  }

  async updateApiKey(id: number, apiKey: ApiKey): Promise<Api> {
    return this.http.post<Api>(`/api/${id}/key`, apiKey).toPromise();
  }

}
