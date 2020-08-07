import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ProfileModule} from '../profile-module/profile.module';
import {WelcomeScreenComponent} from './welcome-screen/welcome-screen.component';
import {TokenInterceptor} from '../auth-module/providers/token.interceptor';
import {RoomModule} from '../room-module/room.module';
import {ToastrModule} from 'ngx-toastr';
import {IonicModule, IonicRouteStrategy} from '@ionic/angular';
import {RouteReuseStrategy} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {MobileMenuComponent} from './mobile-menu/mobile-menu.component';

@NgModule({
    declarations: [
        AppComponent,
        WelcomeScreenComponent,
        MobileMenuComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ProfileModule,
        RoomModule,
        BrowserAnimationsModule,
        HttpClientModule,
        FormsModule,
        IonicModule.forRoot(),
        ToastrModule.forRoot({
                positionClass: 'toast-top-right-under-header',
            }
        ),
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptor,
            multi: true
        },
        {
            provide: RouteReuseStrategy,
            useClass: IonicRouteStrategy
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
