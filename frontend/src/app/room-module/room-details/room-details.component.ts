import {Component, OnInit} from '@angular/core';
import {RoomModel} from '../models/room.model';
import {ActivatedRoute, Router} from '@angular/router';
import {switchMap, tap} from 'rxjs/operators';
import {EMPTY} from 'rxjs';
import {RoomService} from '../providers/room.service';
import {ProfileService} from '../../profile-module/providers/profile.service';

@Component({
    selector: 'app-room-details',
    templateUrl: './room-details.component.html',
    styleUrls: ['./room-details.component.scss']
})
export class RoomDetailsComponent implements OnInit {

    isOwner = false;
    room: RoomModel = new RoomModel();

    constructor(private route: ActivatedRoute,
                private router: Router,
                private roomService: RoomService,
                private profileService: ProfileService) {
    }

    ngOnInit() {
        this.route.params.pipe(
            switchMap(params => +params.id ? this.roomService.fetchRoom(+params.id) : EMPTY),
            tap(room => this.room = room),
            switchMap(() => this.profileService.fetchCurrentUserProfile())
        ).subscribe(currentUser => this.isOwner = currentUser.id === this.room.owner.id);
    }

    book() {
        if (this.isOwner) {
            this.roomService.book(this.room.id).subscribe();
        }
    }
}
