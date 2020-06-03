import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {WebPageTestMeasurement} from './web-page-test-measurement';
import {PageSpeedMeasurement} from './page-speed-measurement';

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  constructor(private http: HttpClient) {
  }

  async getMeasurement(id: number): Promise<WebPageTestMeasurement | PageSpeedMeasurement> {
    return this.http.get<WebPageTestMeasurement | PageSpeedMeasurement>(`/measurement/${id}`).toPromise();
  }

}
