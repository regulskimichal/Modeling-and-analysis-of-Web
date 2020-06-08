import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {SettingRoutingModule} from "./setting-routing.module";
import {SettingsListComponent} from "./settings-list/settings-list.component";


@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        SettingRoutingModule,
    ],
    declarations: [
        SettingsListComponent,
    ]
})
export class SettingModule {
}
