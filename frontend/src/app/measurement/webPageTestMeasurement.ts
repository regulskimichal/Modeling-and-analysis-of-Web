import { Strategy } from '../setting/strategy';
import { ResultType } from './resultType';

export interface WebPageTestMeasurement {
  id: number;
  url: string;
  resultType: ResultType;
  strategy: Strategy | null;
  userAgent: string | null;
  analysisTime: Date;
}
