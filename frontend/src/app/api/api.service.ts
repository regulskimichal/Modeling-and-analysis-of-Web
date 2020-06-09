import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ApiKey } from './api-key';
import { ApiType } from './api-type';
import { ApiKeyDto } from './api-key-dto';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  constructor(private http: HttpClient) {}

  async getApiKey(id: number): Promise<ApiKey> {
    return this.http.get<ApiKey>(`/api-key/${id}`).toPromise();
  }

  async getAllApiKeys(apiType ?: ApiType): Promise<ApiKey[]> {
    if (apiType !== undefined) {
      const params = new HttpParams().set('type', apiType);
      return this.http.get<ApiKey[]>('/api-key', {params}).toPromise();
    } else {
      return this.http.get<ApiKey[]>('/api-key').toPromise();
    }
  }

  async saveApiKey(id: number, apiKeyDto: ApiKeyDto): Promise<ApiKey> {
    return this.http.post<ApiKey>(`/api-key`, apiKeyDto).toPromise();
  }

  async updateApiKey(id: number, apiKeyDto: ApiKeyDto): Promise<void> {
    return this.http.put<void>(`/api-key/${id}`, apiKeyDto).toPromise();
  }

  async deleteApiKey(id: number): Promise<void> {
    return this.http.delete<void>(`/api-key/${id}`).toPromise();
  }

}
