import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AgmCoreModule} from "@agm/core";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {RoomsComponent} from "./rooms/rooms.component";
import {RoomComponent} from "./room/room.component";
import {BrowserComponent} from "./browser/browser.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
    {path: 'find-room', component: RoomsComponent, pathMatch: 'full'},
];

@NgModule({
    declarations: [RoomsComponent, RoomComponent, BrowserComponent],
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
