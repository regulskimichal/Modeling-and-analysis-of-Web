import {Strategy} from '../setting/strategy';
import {ResultType} from './result-type';

export interface WebPageTestMeasurement {
  id: number;
  url: string;
  resultType: ResultType;
  strategy: Strategy | null;
  userAgent: string | null;
  analysisTime: Date;
}
