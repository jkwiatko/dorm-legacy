import {Component, OnDestroy, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {AuthService} from '../../auth-module/providers/auth.service';
import {AlertController, MenuController, NavController} from '@ionic/angular';
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

    constructor(private nav: NavController,
                private auth: AuthService,
                private alertCtrl: AlertController,
                private menuCtrl: MenuController
    ) {
    }

    ngOnInit() {
        this.authSub = this.auth.isLoginIn.subscribe(isLoginIn => this.authenticated = isLoginIn);
    }

    submit(f: NgForm) {
        if (this.isLoggingIn) {
            this.auth.login(f.value)
                .then(this.onSuccessfulLogin.bind(this), this.onError.bind(this));
        } else {
            this.auth.register(f.value)
                .then(this.onSuccessfulLogin.bind(this), this.onError.bind(this));
        }
    }

    logout() {
        this.auth.logout();
    }

    toggle(f: NgForm) {
        f.reset(f.value);
        this.isLoggingIn = !this.isLoggingIn;
    }

    openNavMenu() {
        this.menuCtrl.open('navigation-menu').then();
    }

    handlePressingEnter(f: NgForm, event) {
        if (event && event.key === 'Enter') {
            this.submit(f);
        }
    }

    private onSuccessfulLogin() {
        this.alertCtrl.create({
            message: 'Zalogowano pomyślnie',
            header: 'Witaj!',
            buttons: [{
                text: 'Oki',
                handler: () => this.nav.navigateForward(['profile/edit'])
            }]
        }).then(alert => alert.present());
    }

    private onError(error) {
        if (error.error && error.error.message) {
            this.alertCtrl.create({
                message: error.error.message,
                header: 'Błąd',
                buttons: ['Oki']
            }).then(alert => alert.present());
        } else {
            switch (error.name) {
                case error.name === 'TimeoutError':
                    this.alertCtrl.create({
                        message: error.message,
                        header: 'Błąd',
                        buttons: ['Oki']
                    }).then(alert => alert.present());
                    break;
                case 'HttpErrorResponse':
                    this.alertCtrl.create({
                        message: 'Z nieznanych przyczyn serwer nie odpowiada. ' +
                            'Spróbuj później, a jeśli problem będzie nawracał to skontaktuj sie z administratorem.',
                        header: 'Serwer niedostępny',
                        buttons: ['Oki']
                    }).then(alert => alert.present());
                    break;
                default:
                    console.log('UNKNOWN ERROR!!!');
            }
        }
    }

    ngOnDestroy(): void {
        if (this.authSub) {
            this.authSub.unsubscribe();
        }
    }

}
