import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WebPageTestSettingsComponent } from './setting/webpagetest/web-page-test-settings.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { environment } from '../environments/environment';
import { BACKEND_URL } from './backend.token';
import { BaseUrlInterceptor } from './app.interceptor';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatInputModule } from '@angular/material/input';
import { PageSpeedSettingsComponent } from './setting/pagespeed/page-speed-settings.component';
import { ApiKeySettingsComponent } from './api/settings/api-key-settings.component';
import { HomeComponent } from './home/home.component';
import { MeasurementComponent } from './measurement/measurement.component';

@NgModule({
  declarations: [
    AppComponent,
    WebPageTestSettingsComponent,
    PageSpeedSettingsComponent,
    ApiKeySettingsComponent,
    HomeComponent,
    MeasurementComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    HttpClientModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
  ],
  providers: [{
    provide: BACKEND_URL,
    useValue: environment.backendUrl
  }, {
    provide: HTTP_INTERCEPTORS,
    useClass: BaseUrlInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {}
