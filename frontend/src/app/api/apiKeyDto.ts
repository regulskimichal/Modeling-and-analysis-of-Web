import { ApiType } from './apiType';

export interface ApiKeyDto {
  name: string | null;
  apiKey: string | null;
  defaultKey: boolean | null;
  type: ApiType;
}
