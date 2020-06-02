import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WebPageTestMeasurement } from './webPageTestMeasurement';
import { PageSpeedMeasurement } from './pageSpeedMeasurement';

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  constructor(private http: HttpClient) {}

  async getMeasurement(id: number): Promise<WebPageTestMeasurement | PageSpeedMeasurement> {
    return this.http.get<WebPageTestMeasurement | PageSpeedMeasurement>(`/measurement/${id}`).toPromise();
  }

}
