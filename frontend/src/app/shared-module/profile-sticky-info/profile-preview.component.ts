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
    @Input() roomIdToPickRoommate: number;

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    navigateToUser() {
        if (this.navigateOnClick) {
            if (this.roomIdToPickRoommate) {
                this.router.navigate(
                    ['profile', this.profile.id],
                    {state: {roomId: this.roomIdToPickRoommate}}).then();
            } else {
                this.router.navigate(['profile', this.profile.id]).then();
            }

        }
    }
}
