import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AgmCoreModule} from "@agm/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {RouterModule, Routes} from "@angular/router";
import {RoomsComponent} from "./room-search/room-list/rooms.component";
import {RoomComponent} from "./room-search/room-item/room.component";
import {BrowserComponent} from "./room-search/room-browser/browser.component";
import { RoomEditComponent } from './room-edit/room-edit.component';

const routes: Routes = [
    {path: 'room/find', component: RoomsComponent, pathMatch: 'full'},
    {path: 'room/edit/:id', component: RoomEditComponent, pathMatch: 'full'},
];

@NgModule({
    declarations: [RoomsComponent, RoomComponent, BrowserComponent, RoomEditComponent],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        AgmCoreModule,
        MatSelectModule,
        MatInputModule,
    ],
})
export class RoomModule {
}
