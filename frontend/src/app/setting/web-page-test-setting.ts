import {Browser} from './browser';
import {ConnectivityProfile} from './connectivityProfile';

export class WebPageTestSetting {
  id: number;
  pageUrl: string;
  apiKeyId: number;
  cronExpression: string;
  zoneId: string;
  location: string | null;
  browser: Browser | null;
  connectivityProfile: ConnectivityProfile | null;
}
