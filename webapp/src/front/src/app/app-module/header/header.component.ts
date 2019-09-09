import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RegisterComponent} from '../../auth-module/component/register/register.component';
import {MatDialog, MatSnackBar} from '@angular/material';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

    constructor(private router: Router,
                private dialog: MatDialog,
                private toast: MatSnackBar) { }

    ngOnInit() {
    }

    getPositioning() {
        if (this.router.url === '/home') {
            return 'absolute-header';
        } else {
            return 'sticky-header';
        }
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
}
