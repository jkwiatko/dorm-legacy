import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeModule } from '../home-module/home.module';
import {HeaderComponent} from './header/header.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent },
  {path: '', redirectTo: 'home', pathMatch: 'full'},
];

@NgModule({
  imports: [
    HomeModule,
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
