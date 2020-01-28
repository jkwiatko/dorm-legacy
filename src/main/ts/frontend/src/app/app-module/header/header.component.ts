import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RegisterComponent} from '../../auth-module/component/register/register.component';
import {MatDialog, MatSnackBar} from '@angular/material';
import {LoginComponent} from '../../auth-module/component/login/login.component';
import {AuthService} from "../../auth-module/provider/auth.service";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

    authenticated = false;

    constructor(private router: Router,
                private dialog: MatDialog,
                private toast: MatSnackBar,
                private auth: AuthService,
                ) {
    }

    ngOnInit() {
        this.auth.isLoginIn.subscribe(isLoginIn => this.authenticated = isLoginIn);
    }

    isHome() {
        return this.router.url === '/home';
    }

    onBack() {
        this.router.navigate(['/']);
    }

    logout() {

    }

    openRegisterDialog() {
        this.dialog.open(RegisterComponent, {
            disableClose: true,
            width: '600px',
        }).afterClosed().subscribe(result => {
            if (result === 'success') {
                this.toast.open('Witamy username', null, {
                    duration: 3000,
                    verticalPosition: 'top'
                });
            }
        });
    }

    openLoginDialog() {
        this.dialog.open(LoginComponent, {
            disableClose: true,
            width: '600px',
        }).afterClosed().subscribe(result => {
            if (result === 'success') {
                this.toast.open('Witamy username', null, {
                    duration: 3000,
                    verticalPosition: 'top'
                });
            }
        });
    }
}
