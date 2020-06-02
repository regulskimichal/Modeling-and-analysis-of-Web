import { Strategy } from './strategy';

export interface PageSpeedSetting {
  id: number;
  pageUrl: string;
  apiKeyId: number;
  cronExpression: string;
  zoneId: string;
  strategy: Strategy | null;
}
