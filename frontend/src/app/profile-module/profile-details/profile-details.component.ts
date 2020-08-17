import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../providers/profile.service';
import {ActivatedRoute, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {ProfileModel} from '../models/profile.model';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';
import {RoomService} from '../../room-module/providers/room.service';

@Component({
    selector: 'app-profile-details',
    templateUrl: './profile-details.component.html',
    styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements OnInit {

    constructor(
        private profileService: ProfileService,
        private roomService: RoomService,
        private route: ActivatedRoute,
        private router: Router
    ) {
    }

    roomIdToPickRoommate: number
    profile: ProfileModel;
    profilePreview = new ProfilePreviewModel();

    ngOnInit() {
        const routerState = this.router.getCurrentNavigation().extras.state;
        if (routerState && routerState.roomId) {
            this.roomIdToPickRoommate = routerState.roomId;
        }
        this.profile = new ProfileModel();
        this.route.params.pipe(
            switchMap(params => this.profileService.fetchUserProfile(params.id))
        ).subscribe(profile => {
            this.profile = profile;
            this.profilePreview = ProfilePreviewModel.buildFromProfileModel(profile);
        });
    }

    pickRoommate() {
        this.roomService.pickRoommate(+this.roomIdToPickRoommate, this.profile.id).subscribe();
    }
}
