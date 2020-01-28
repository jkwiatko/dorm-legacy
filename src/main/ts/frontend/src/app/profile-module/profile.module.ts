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

const routes: Routes = [
    {path: 'profile/edit', component: ProfileEditComponent},
    {path: 'profile/:id', component: ProfileDetailsComponent}
];

@NgModule({
    declarations: [ProfileDetailsComponent, ProfileEditComponent, AgePipe, GenderPipe],
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
