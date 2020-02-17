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
import {RouterModule, Routes} from "@angular/router";
import {SharedModule} from "../shared-module/shared.module";
import {AuthGuardService} from "../auth-module/providers/auth-guard.service";

const routes: Routes = [
    {path: 'profile/edit', component: ProfileEditComponent, canActivate: [AuthGuardService]},
    {path: 'profile/:id', component: ProfileDetailsComponent}
];

@NgModule({
    declarations: [ProfileDetailsComponent, ProfileEditComponent],
    exports: [],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        MatRadioModule,
        SharedModule
    ]
})
export class ProfileModule {
}
