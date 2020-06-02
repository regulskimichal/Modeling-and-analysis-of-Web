import { Strategy } from '../setting/strategy';
import { ResultType } from './resultType';

export interface PageSpeedMeasurement {
  id: number;
  resultType: ResultType;
  strategy: Strategy | null;
  userAgent: string | null;
  analysisTime: Date;
  firstContentfulPaint: number | null;
  firstMeaningfulPaint: number | null;
  largestContentfulPaint: number | null;
  maxPotentialFirstInputDelay: number | null;
  speedIndex: number | null;
}
