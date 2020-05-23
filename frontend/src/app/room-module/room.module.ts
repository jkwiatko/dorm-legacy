import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AgmCoreModule} from "@agm/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {RouterModule, Routes} from "@angular/router";
import {RoomSearchComponent} from "./room-search/room-search.component";
import { RoomEditComponent } from './room-edit/room-edit.component';
import {ReactiveFormsModule} from "@angular/forms";
import {ProfileModule} from "../profile-module/profile.module";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {SharedModule} from "../shared-module/shared.module";
import {AuthGuardService} from "../auth-module/providers/auth-guard.service";
import { RoomDetailsComponent } from './room-details/room-details.component';
import {MatRadioModule} from "@angular/material/radio";

const routes: Routes = [
    {path: 'room/find', component: RoomSearchComponent, pathMatch: 'full'},
    {path: 'room/create', component: RoomEditComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/:id', component: RoomDetailsComponent, pathMatch: 'full'},
    {path: 'room/edit/:id', component: RoomEditComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
];

@NgModule({
    declarations: [RoomSearchComponent, RoomEditComponent, RoomDetailsComponent],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        AgmCoreModule,
        MatSelectModule,
        MatInputModule,
        ReactiveFormsModule,
        ProfileModule,
        MatDatepickerModule,
        MatDatepickerModule,
        SharedModule,
        MatRadioModule,
    ],
})
export class RoomModule {
}
