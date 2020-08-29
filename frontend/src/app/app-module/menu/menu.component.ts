import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MenuController} from '@ionic/angular';
import {AuthService} from '../../auth-module/providers/auth.service';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

    constructor(
        private router: Router,
        private auth: AuthService,
        private menuCtrl: MenuController,
    ) {
    }

    ngOnInit(): void {
    }

    navigate() {
        this.menuCtrl.close('navigation-menu').then();
    }

    logout() {
        this.auth.logout();
        this.menuCtrl.close('navigation-menu').then();
    }
}
