import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SettingsListComponent } from './settings-list/settings-list.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
  ],
  declarations: [
    SettingsListComponent,
  ]
})
export class SettingModule {}
