import { Inject, Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BACKEND_URL } from './backend.token';

@Injectable()
export class BaseUrlInterceptor implements HttpInterceptor {

  constructor(@Inject(BACKEND_URL) private backendUrl: string) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      url: this.backendUrl + req.url
    });
    return next.handle(req);
  }

}
