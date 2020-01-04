import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModuleModule} from '../auth-module/auth-module.module';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TokenInterceptor} from './provider/token.interceptor';
import {MatSnackBarModule} from '@angular/material';
import {AgmCoreModule} from '@agm/core';
import {ProfileModule} from "../profile-module/profile.module";

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        AuthModuleModule,
        ProfileModule,
        BrowserAnimationsModule,
        MatSnackBarModule,
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
