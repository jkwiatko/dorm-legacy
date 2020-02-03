import {Component, Input, OnInit} from '@angular/core';
import {Profile} from "../model/profile";

@Component({
  selector: 'app-profile-sticky-info',
  templateUrl: './profile-sticky-info.component.html',
  styleUrls: ['./profile-sticky-info.component.scss']
})
export class ProfileStickyInfoComponent implements OnInit {

  @Input() profile: Profile;

  constructor() { }

  ngOnInit() {
  }

}
