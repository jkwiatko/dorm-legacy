import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProfileEditComponent} from "./profile-edit/profile-edit.component";
import {ProfileDetailsComponent} from "./profile-details/profile-details.component";

const routes: Routes = [
    {path: 'profile/edit', component: ProfileEditComponent},
    {path: 'profile/:id', component: ProfileDetailsComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes),
    ],
    exports: [RouterModule]
})
export class ProfileRoutingModule {
}
