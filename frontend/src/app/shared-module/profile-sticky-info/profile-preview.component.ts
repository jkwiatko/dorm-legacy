import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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
    roomIdToPickRoommate: number;

    constructor(private router: Router, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => this.roomIdToPickRoommate = params.roomId)
    }

    navigateToUser() {
        if (this.navigateOnClick) {
            if (this.roomIdToPickRoommate) {
                this.router.navigate(
                    ['room',this.roomIdToPickRoommate, 'search', 'rentee', this.profile.id]).then();
            } else {
                this.router.navigate(['profile', this.profile.id]).then();
            }

        }
    }
}
