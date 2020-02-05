import { Component, OnInit } from '@angular/core';
import {ProfileService} from "../providers/profile.service";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {ProfileModel} from "../model/profile.model";

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {

  constructor(private profileCli: ProfileService, private route: ActivatedRoute) { }

  profileSub: Subscription;
  profile: ProfileModel;

  ngOnInit() {
      this.profile = new ProfileModel();
      this.profileSub = this.route.params.pipe(
          switchMap(params => this.profileCli.fetchUserProfile(params['id']))
      ).subscribe(profile => this.profile = profile);
  }
}
