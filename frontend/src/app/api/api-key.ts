import { ApiType } from './api-type';
import { ApiTypeProvider } from './api-type-provider';

export interface ApiKey extends ApiTypeProvider {
  id: number;
  name: string | null;
  apiKey: string | null;
  defaultKey: boolean;
  type: ApiType;
}
