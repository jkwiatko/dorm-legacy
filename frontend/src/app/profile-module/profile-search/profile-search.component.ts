import {Component, OnInit} from '@angular/core';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';

@Component({
    selector: 'app-profile-search',
    templateUrl: './profile-search.component.html',
    styleUrls: ['./profile-search.component.scss']
})
export class ProfileSearchComponent implements OnInit {
    isLoading = false;
    profiles: ProfilePreviewModel[];

    constructor() {
    }

    ngOnInit(): void {
    }

    navigateToProfile(id: any) {
        return null
    }
}
