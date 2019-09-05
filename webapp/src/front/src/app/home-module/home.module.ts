import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeScreenComponent } from './welcome-screen/welcome-screen.component';
import {HomeRoutingModule} from './home-routing.module';

@NgModule({
  declarations: [WelcomeScreenComponent],
  imports: [
    HomeRoutingModule,
    CommonModule
  ]
})
export class HomeModule { }
