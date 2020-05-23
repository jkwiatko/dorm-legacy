import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModule} from '../auth-module/auth.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {MatSnackBarModule} from '@angular/material';
import {AgmCoreModule} from '@agm/core';
import {ProfileModule} from "../profile-module/profile.module";
import {WelcomeScreenComponent} from "./welcome-screen/welcome-screen.component";
import {TokenInterceptor} from "../auth-module/providers/token.interceptor";
import {RoomModule} from "../room-module/room.module";
import {ToastrModule} from "ngx-toastr";

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        WelcomeScreenComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        AuthModule,
        ProfileModule,
        RoomModule,
        BrowserAnimationsModule,
        MatSnackBarModule,
        ToastrModule.forRoot({
                positionClass: 'toast-top-right-under-header',
            }
        ),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyDKVy1CJI89TnmbC381IzCvQumuddcZttY'
        })
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptor,
            multi: true
        },
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}