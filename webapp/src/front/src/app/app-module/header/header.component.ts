import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RegisterComponent} from '../../auth-module/component/register/register.component';
import {MatDialog} from '@angular/material';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

    constructor(private router: Router,
                private dialog: MatDialog) { }

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
        });
    }
}
