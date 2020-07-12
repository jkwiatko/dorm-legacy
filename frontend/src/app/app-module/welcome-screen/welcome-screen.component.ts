import {Component, OnDestroy, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {AuthService} from '../../auth-module/providers/auth.service';
import {AlertController, MenuController} from '@ionic/angular';
import {NgForm} from '@angular/forms';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-welcome-screen',
    templateUrl: './welcome-screen.component.html',
    styleUrls: ['./welcome-screen.component.scss'],
})
export class WelcomeScreenComponent implements OnInit, OnDestroy {
    authenticated = false;
    authSub: Subscription;
    mobile = environment.mobile;
    isLoggingIn = true;
    isLoading = false;

    constructor(private router: Router,
                private auth: AuthService,
                private alertCtrl: AlertController,
                private menuCtrl: MenuController
    ) {
    }

    ngOnInit() {
       this.authSub = this.auth.isLoginIn.subscribe(isLoginIn => this.authenticated = isLoginIn);
    }

    login(f: NgForm) {
        this.auth.login(f.value).then(this.onSuccessfulLogin.bind(this), this.onError.bind(this));
    }

    register(f: NgForm) {
        this.auth.register(f.value).then(this.onSuccessfulLogin.bind(this), this.onError.bind(this));
    }

    onSuccessfulLogin() {
        this.alertCtrl.create({
            message: 'Zalogowano pomyślnie',
            header: 'Witaj!',
            buttons: [{
                text: 'Oki',
                handler: () => this.router.navigate(['profile/edit'])
            }]
        }).then(alert => alert.present());
    }

    onError(error) {
        if (error.error && error.error.message) {
            this.alertCtrl.create({
                message: error.error.message,
                header: 'Błąd',
                buttons: ['Oki']
            }).then(alert => alert.present());
        } else {
            if (error.name === 'TimeoutError') {
                this.alertCtrl.create({
                    message: error.message,
                    header: 'Błąd',
                    buttons: ['Oki']
                }).then(alert => alert.present());
            } else {
                console.log('CRITICAL ERROR!');
            }
        }
    }

    toggle(f: NgForm) {
        f.reset(f.value);
        this.isLoggingIn = !this.isLoggingIn;
    }

    logout() {
        this.auth.logout();
    }

    ngOnDestroy(): void {
        if (this.authSub) {
            this.authSub.unsubscribe();
        }
    }

    openNavMenu() {
        this.menuCtrl.open('navigation-menu').then();
    }
}
