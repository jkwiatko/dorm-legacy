import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ProfilePreviewModel} from '../models/profile-preview.model';


@Component({
  selector: 'app-profile-sticky-info',
  templateUrl: './profile-sticky-info.component.html',
  styleUrls: ['./profile-sticky-info.component.scss']
})
export class ProfileStickyInfoComponent implements OnInit {

  @Input() profile: ProfilePreviewModel;
  @Input() navigateOnClick: boolean;

  constructor(private router: Router) { }

  ngOnInit() {
  }

    navigateToUser() {
      if(this.navigateOnClick) {
          this.router.navigate(['profile', this.profile.id]).then();
      }
    }
}
