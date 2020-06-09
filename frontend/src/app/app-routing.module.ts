import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MeasurementComponent } from './measurement/measurement.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'measurement/:id', component: MeasurementComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true} // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
