import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WelcomeScreenComponent} from './welcome-screen/welcome-screen.component';
import {HomeRoutingModule} from './home-routing.module';
import {RoomsComponent} from './rooms/rooms.component';
import { RoomComponent } from './room/room.component';
import {AgmCoreModule} from '@agm/core';
import { BrowserComponent } from './browser/browser.component';
import {MatInputModule, MatSelectModule} from '@angular/material';

@NgModule({
    declarations: [WelcomeScreenComponent, RoomsComponent, RoomComponent, BrowserComponent],
    imports: [
        HomeRoutingModule,
        CommonModule,
        AgmCoreModule,
        MatSelectModule,
        MatInputModule
    ]
})
export class HomeModule {
}
