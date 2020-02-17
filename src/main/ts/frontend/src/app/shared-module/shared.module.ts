import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SafePipe} from './pipes/safe.pipe';
import {RoomPreviewComponent} from './room-preview/room-preview.component';
import {ProfileStickyInfoComponent} from "./profile-sticky-info/profile-sticky-info.component";
import {AgePipe} from "./pipes/age.pipe";
import {GenderPipe} from "./pipes/gender.pipe";
import {AvailableFromPipe} from "./pipes/available-from.pipe";

@NgModule({
    declarations: [SafePipe, AgePipe, GenderPipe, ProfileStickyInfoComponent, RoomPreviewComponent, AvailableFromPipe],
    exports: [
        SafePipe,
        AgePipe,
        GenderPipe,
        ProfileStickyInfoComponent,
        RoomPreviewComponent,
        AvailableFromPipe
    ],
    imports: [
        CommonModule
    ]
})
export class SharedModule {
}
