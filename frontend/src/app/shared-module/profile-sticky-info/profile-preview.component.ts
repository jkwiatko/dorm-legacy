import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ProfilePreviewModel} from '../models/profile-preview.model';


@Component({
  selector: 'app-profile-preview',
  templateUrl: './profile-preview.component.html',
  styleUrls: ['./profile-preview.component.scss']
})
export class ProfilePreviewComponent implements OnInit {

  @Input() profile: ProfilePreviewModel;
  @Input() navigateOnClick: boolean;
  @Input() sticky: boolean;

  constructor(private router: Router) { }

  ngOnInit() {
  }

    navigateToUser() {
      if(this.navigateOnClick) {
          this.router.navigate(['profile', this.profile.id]).then();
      }
    }
}
