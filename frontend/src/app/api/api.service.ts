import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ApiKey } from './apiKey';
import { ApiType } from './apiType';
import { ApiKeyDto } from './apiKeyDto';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  async getApiKey(id: number): Promise<ApiKey> {
    return this.http.get<ApiKey>(`/api-key/${id}`).toPromise();
  }

  async getAllApiKeys(apiKeyType: ApiType | null = null): Promise<ApiKey[]> {
    const params = new HttpParams().set('type', apiKeyType);
    return this.http.get<ApiKey[]>('/api-key', {params}).toPromise();
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
