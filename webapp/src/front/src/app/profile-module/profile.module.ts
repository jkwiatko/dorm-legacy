import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from './profile.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';

@NgModule({
    declarations: [ProfileCardComponent, ProfileComponent, ProfileDetailsComponent,],
    imports: [
        ProfileRoutingModule,
        CommonModule
    ]
})
export class ProfileModule { }
