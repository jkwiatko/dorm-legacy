import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from './profile.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';

@NgModule({
    declarations: [ProfileComponent, ProfileDetailsComponent, ProfileEditComponent],
    imports: [
        ProfileRoutingModule,
        CommonModule
    ]
})
export class ProfileModule { }
