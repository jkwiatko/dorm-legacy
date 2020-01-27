import { Component, OnInit } from '@angular/core';
import {ProfileService} from "../providers/profile.service";
import {Subscription} from "rxjs";
import {Profile} from "../profile-edit/profile-edit.component";
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {

  constructor(private profileCli: ProfileService, private route: ActivatedRoute) { }

  profileSub: Subscription;
  profile: Profile;

  ngOnInit() {
      this.profile = new Profile();
      this.profileSub = this.route.params.pipe(
          switchMap(params => this.profileCli.fetchUserProfile(params['id']))
      ).subscribe(profile => {
          this.profile = profile;
      });
  }
}
