import {Component, OnInit} from '@angular/core';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';
import {ActivatedRoute} from '@angular/router';
import {ProfileService} from '../providers/profile.service';
import {switchMap} from 'rxjs/operators';

@Component({
    selector: 'app-profile-search',
    templateUrl: './roommate-search.component.html',
    styleUrls: ['./roommate-search.component.scss']
})
export class RoommateSearchComponent implements OnInit {

    isLoading = false;
    profiles: ProfilePreviewModel[] = [];

    constructor(private route: ActivatedRoute,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.route.params.pipe(
            switchMap(params => this.profileService.fetchUserProfilePreviewsForRoom(params.id)))
            .subscribe(profiles => {
                console.log(profiles);
                this.profiles = profiles
            });
    }

    navigateToProfile(id: any) {
        return null
    }
}
