import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileDetailsComponent} from './profile-details/profile-details.component';
import {ProfileEditComponent} from './profile-edit/profile-edit.component';
import {ReactiveFormsModule} from "@angular/forms";
import {
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    MatRadioModule
} from "@angular/material";
import {AgePipe} from './pipes/age.pipe';
import {GenderPipe} from './pipes/gender.pipe';
import {RouterModule, Routes} from "@angular/router";
import { ProfileStickyInfoComponent } from './profile-sticky-info/profile-sticky-info.component';

const routes: Routes = [
    {path: 'profile/edit', component: ProfileEditComponent},
    {path: 'profile/:id', component: ProfileDetailsComponent}
];

@NgModule({
    declarations: [ProfileDetailsComponent, ProfileEditComponent, AgePipe, GenderPipe, ProfileStickyInfoComponent],
    exports: [
        ProfileStickyInfoComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        MatRadioModule
    ]
})
export class ProfileModule {
}
