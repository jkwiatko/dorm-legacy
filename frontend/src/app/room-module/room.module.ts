import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {RouterModule, Routes} from '@angular/router';
import {RoomSearchComponent} from './room-search/room-search.component';
import {RoomEditComponent} from './room-edit/room-edit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ProfileModule} from '../profile-module/profile.module';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {SharedModule} from '../shared-module/shared.module';
import {AuthGuardService} from '../auth-module/providers/auth-guard.service';
import {RoomDetailsComponent} from './room-details/room-details.component';
import {MatRadioModule} from '@angular/material/radio';
import {IonicModule} from '@ionic/angular';
import {SafePipe} from '../shared-module/pipes/safe.pipe';

const routes: Routes = [
    {path: 'room/search', component: RoomSearchComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/search/own', component: RoomSearchComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/search/reserved', component: RoomSearchComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/search/rented', component: RoomSearchComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/create', component: RoomEditComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/:id/edit', component: RoomEditComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
    {path: 'room/:id', component: RoomDetailsComponent, pathMatch: 'full', canActivate: [AuthGuardService]},
];

@NgModule({
    declarations: [RoomSearchComponent, RoomEditComponent, RoomDetailsComponent],
    imports: [
        CommonModule,
        MatSelectModule,
        MatInputModule,
        ReactiveFormsModule,
        ProfileModule,
        MatDatepickerModule,
        MatDatepickerModule,
        SharedModule,
        MatRadioModule,
        IonicModule,
        FormsModule,
        RouterModule.forChild(routes),
    ],
    providers: [SafePipe]
})
export class RoomModule {
}
