import { ApiType } from './apiType';

export interface ApiKey {
  id: number;
  name: string | null;
  apiKey: string | null;
  defaultKey: boolean;
  type: ApiType;
}
