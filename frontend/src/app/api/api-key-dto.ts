import {ApiType} from './api-type';

export interface ApiKeyDto {
  name: string | null;
  apiKey: string | null;
  defaultKey: boolean | null;
  type: ApiType;
}
