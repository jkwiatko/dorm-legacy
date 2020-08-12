import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SafePipe} from './pipes/safe.pipe';
import {RoomPreviewComponent} from './room-preview/room-preview.component';
import {ProfilePreviewComponent} from './profile-sticky-info/profile-preview.component';
import {AgePipe} from './pipes/age.pipe';
import {GenderPipe} from './pipes/gender.pipe';
import {AvailableFromPipe} from './pipes/available-from.pipe';
import {LoaderSpinnerComponent} from './loader-spiner/loader-spinner.component';
import {DateParserPipe} from './pipes/dateParser.pipe';
import {RoomNumberPipe} from './pipes/room-number.pipe';

@NgModule({
    declarations: [
        SafePipe,
        AgePipe,
        GenderPipe,
        ProfilePreviewComponent,
        RoomPreviewComponent,
        AvailableFromPipe,
        LoaderSpinnerComponent,
        DateParserPipe,
        RoomNumberPipe
    ],
    exports: [
        SafePipe,
        AgePipe,
        GenderPipe,
        DateParserPipe,
        ProfilePreviewComponent,
        RoomPreviewComponent,
        AvailableFromPipe,
        LoaderSpinnerComponent,
        RoomNumberPipe
    ],
    imports: [
        CommonModule
    ]
})
export class SharedModule {
}
