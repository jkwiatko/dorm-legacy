import {Component, Input, OnInit} from '@angular/core';
import {ProfilePreviewModel} from '../models/profile-preview.model';

@Component({
    selector: 'app-profile-preview',
    templateUrl: './profile-preview.component.html',
    styleUrls: ['./profile-preview.component.scss']
})
export class ProfilePreviewComponent implements OnInit {

    @Input()
    profile: ProfilePreviewModel;

    constructor() {
    }

    ngOnInit(): void {
        console.log(this.profile);
    }

    navigateToUser() {
        return null;
    }
}
