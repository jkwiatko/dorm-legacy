import {Component, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {AuthService} from '../../auth-module/providers/auth.service';
import {AlertController} from '@ionic/angular';
import {NgForm} from '@angular/forms';

@Component({
    selector: 'app-welcome-screen',
    templateUrl: './welcome-screen.component.html',
    styleUrls: ['./welcome-screen.component.scss'],
})
export class WelcomeScreenComponent implements OnInit {
    authenticated = false;
    mobile = environment.mobile;
    isLogging = true;

    constructor(private router: Router,
                private auth: AuthService,
                private alertCtrl: AlertController
    ) {
    }

    ngOnInit() {
        this.auth.isLoginIn.subscribe(isLoginIn => this.authenticated = isLoginIn);
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
            buttons: ['Oki']
        }).then(alert => alert.present());
    }

    onError(error) {
        this.alertCtrl.create({
            message: error.message,
            header: 'error',
            buttons: ['Oki']
        }).then(alert => alert.present());
    }

    toggle(f: NgForm) {
        f.reset();
        this.isLogging = !this.isLogging;
    }
}
