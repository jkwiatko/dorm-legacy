import {Component} from '@angular/core';
import {RoomModel} from '../models/room.model';
import {ActivatedRoute, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {EMPTY} from 'rxjs';
import {RoomService} from '../providers/room.service';
import {ProfileService} from '../../profile-module/providers/profile.service';
import {NavController, ViewWillEnter} from '@ionic/angular';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';

@Component({
    selector: 'app-room-details',
    templateUrl: './room-details.component.html',
    styleUrls: ['./room-details.component.scss']
})
export class RoomDetailsComponent implements ViewWillEnter {

    isOwner = false;
    isBooked = false;
    isLoading = true;
    room: RoomModel = new RoomModel();
    ownerProfilePreview = new ProfilePreviewModel();

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private roomService: RoomService,
        private profileService: ProfileService,
        private nav: NavController
    ) {
    }

    ionViewWillEnter() {
        this.route.params.pipe(
            switchMap(params => +params.id ? this.roomService.fetchRoom(+params.id) : EMPTY),
            switchMap(room => {
                this.room = room;
                this.ownerProfilePreview = ProfilePreviewModel.buildFromProfileModel(room.owner);
                return this.profileService.fetchCurrentUserProfile()
            }),
            switchMap(currentUser => {
                this.isOwner = currentUser.id === this.room.owner.id;
                return this.roomService.isBooked(this.room.id);
            })
        ).subscribe(isBooked => {
            this.isBooked = isBooked;
            this.isLoading = false;
        });
    }

    book() {
        this.roomService.book(this.room.id).subscribe();
        this.nav.navigateBack(['room/search']).then();
    }

    unBook() {
        this.roomService.unBook(this.room.id).subscribe();
        this.nav.navigateBack(['room/search/reserved']).then();
    }
}
