import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WelcomeScreenComponent} from './welcome-screen/welcome-screen.component';
import {HomeRoutingModule} from './home-routing.module';
import {RoomsComponent} from './rooms/rooms.component';
import { RoomComponent } from './room/room.component';
import {AgmCoreModule} from "@agm/core";

@NgModule({
    declarations: [WelcomeScreenComponent, RoomsComponent, RoomComponent],
    imports: [
        HomeRoutingModule,
        CommonModule,
        AgmCoreModule
    ]
})
export class HomeModule {
}
