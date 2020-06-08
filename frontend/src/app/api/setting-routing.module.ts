import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApiKeySettingsComponent} from "./settings/api-key-settings.component";
import {SettingsListComponent} from "./settings-list/settings-list.component";


const settingRoutes: Routes = [
    {
        path: '',
        component: ApiKeySettingsComponent,
        children: [
            {
                path: '',
                component: ApiKeySettingsComponent,
                children: [
                    {
                        path: ':id',
                        component: SettingsListComponent,

                    },
                    {
                        path: '',
                        component: ApiKeySettingsComponent,
                    }
                ]
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(settingRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class SettingRoutingModule {
}

