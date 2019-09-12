import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeModule } from '../home-module/home.module';
import {ProfileModule} from "../profile-module/profile.module";

const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
    imports: [
        HomeModule,
        ProfileModule,
        RouterModule.forRoot(routes),
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }
