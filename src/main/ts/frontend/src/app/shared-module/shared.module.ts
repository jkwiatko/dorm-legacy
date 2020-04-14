import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SafePipe} from './pipes/safe.pipe';
import {RoomPreviewComponent} from './room-preview/room-preview.component';
import {ProfileStickyInfoComponent} from "./profile-sticky-info/profile-sticky-info.component";
import {AgePipe} from "./pipes/age.pipe";
import {GenderPipe} from "./pipes/gender.pipe";
import {AvailableFromPipe} from "./pipes/available-from.pipe";
import {LoaderSpinnerComponent} from "./loader-spiner/loader-spinner.component";
import { DateParserPipe } from './pipes/dateParser.pipe';

@NgModule({
    declarations: [SafePipe, AgePipe, GenderPipe, ProfileStickyInfoComponent, RoomPreviewComponent, AvailableFromPipe, LoaderSpinnerComponent, DateParserPipe],
    exports: [
        SafePipe,
        AgePipe,
        GenderPipe,
        DateParserPipe,
        ProfileStickyInfoComponent,
        RoomPreviewComponent,
        AvailableFromPipe,
        LoaderSpinnerComponent
    ],
    imports: [
        CommonModule
    ]
})
export class SharedModule {
}
