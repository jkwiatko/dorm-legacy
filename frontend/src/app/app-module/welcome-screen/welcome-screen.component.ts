import {Component, OnDestroy, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from '../../auth-module/providers/auth.service';
import {AlertController} from '@ionic/angular';
import {NgForm} from '@angular/forms';
import {switchMap, timeout} from 'rxjs/operators';
import {TokenModel} from '../../auth-module/models/token.model';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-welcome-screen',
    templateUrl: './welcome-screen.component.html',
    styleUrls: ['./welcome-screen.component.scss'],
})
export class WelcomeScreenComponent implements OnInit, OnDestroy {
    authenticated = false;
    mobile = environment.mobile;
    isLogging = true;
    private registerSub: Subscription;
    private loginSub: Subscription;

    constructor(private router: Router,
                private dialog: MatDialog,
                private auth: AuthService,
                private alertCtrl: AlertController
    ) {
    }

    ngOnInit() {
        this.auth.isLoginIn.subscribe(isLoginIn => this.authenticated = isLoginIn);
    }

    login(f: NgForm) {
        this.loginSub = this.auth.login(f.value)
            .pipe(timeout(2000))
            .subscribe(
                token => {
                    this.auth.addAccessToken(new TokenModel().merge(token));
                    this.alertCtrl.create({
                        message: 'Zalogowano pomyślnie',
                        header: 'Witaj!',
                        buttons: ['Oki']
                    }).then(alert => alert.present());
                },
                error => this.alertCtrl.create({
                    message: error.message,
                    header: 'error',
                    buttons: ['Oki']
                }).then(alert => alert.present())
            );
    }

    register(f: NgForm) {
        this.registerSub = this.auth.register(f.value)
            .pipe(switchMap(() => this.auth.login(f.value)))
            .subscribe(token => this.auth.addAccessToken(new TokenModel().merge(token)));
    }

    toggle(f: NgForm) {
        f.reset();
        this.isLogging = !this.isLogging;
    }

    ngOnDestroy(): void {
        if (this.registerSub) {
            this.registerSub.unsubscribe();
        }
        if (this.loginSub) {
            this.loginSub.unsubscribe();
        }
    }
}
