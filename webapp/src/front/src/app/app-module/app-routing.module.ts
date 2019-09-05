import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeModule } from '../home-module/home.module';
import {HeaderComponent} from './header/header.component';

const routes: Routes = [
  {path: '', component: HeaderComponent, outlet: 'header'},
];

@NgModule({
  imports: [
    HomeModule,
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
