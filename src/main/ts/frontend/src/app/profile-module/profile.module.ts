import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';
import {ReactiveFormsModule} from "@angular/forms";
import {
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    MatRadioModule
} from "@angular/material";
import { AgePipe } from './pipes/age.pipe';
import { GenderPipe } from './pipes/gender.pipe';

@NgModule({
    declarations: [ProfileDetailsComponent, ProfileEditComponent, AgePipe, GenderPipe],
    imports: [
        ProfileRoutingModule,
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        MatRadioModule
    ]
})
export class ProfileModule { }
