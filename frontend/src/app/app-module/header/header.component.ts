import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RegisterComponent} from "../../auth-module/auth-register/register.component";
import {LoginComponent} from "../../auth-module/auth-login/login.component";
import {AuthService} from "../../auth-module/providers/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";

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
        this.auth.logout();
    }

    openRegisterDialog() {
        this.dialog.open(RegisterComponent, {
            disableClose: true,
            width: '600px',
        }).afterClosed().subscribe(result => {
            this.displayWelcomeToast(result);
        });
    }

    openLoginDialog() {
        this.dialog.open(LoginComponent, {
            disableClose: true,
            width: '600px',
        }).afterClosed().subscribe(result => {
            this.displayWelcomeToast(result);
        });
    }

    private displayWelcomeToast(result) {
        if (result === 'success') {
            this.toast.open('Witamy', null, {
                duration: 3000,
                verticalPosition: 'top'
            });
        }
    }
}
