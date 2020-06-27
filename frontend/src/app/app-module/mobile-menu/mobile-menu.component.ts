import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MenuController} from '@ionic/angular';

@Component({
  selector: 'app-mobile-menu',
  templateUrl: './mobile-menu.component.html',
  styleUrls: ['./mobile-menu.component.scss']
})
export class MobileMenuComponent implements OnInit {

  constructor(
      private router: Router,
      private menuCtrl: MenuController,
  ) { }

  ngOnInit(): void {
  }

    navigate() {
       this.menuCtrl.close('navigation-menu').then();
    }
}
