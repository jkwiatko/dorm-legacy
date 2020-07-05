import {Component, OnDestroy, OnInit} from '@angular/core';
import {RoomModel} from '../models/room.model';
import {ActivatedRoute, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {EMPTY, Subscription} from 'rxjs';
import {RoomService} from '../providers/room.service';

@Component({
    selector: 'app-room-details',
    templateUrl: './room-details.component.html',
    styleUrls: ['./room-details.component.scss']
})
export class RoomDetailsComponent implements OnInit, OnDestroy {

    room: RoomModel = new RoomModel();
    sub: Subscription;

    constructor(private route: ActivatedRoute, private router: Router, private roomService: RoomService) {
    }

    ngOnInit() {
        this.sub = this.route.params.pipe(
            switchMap(params => +params.id ? this.roomService.fetchRoom(+params.id) : EMPTY)
        ).subscribe(room => this.room = room);
    }

    ngOnDestroy(): void {
        this.sub.unsubscribe();
    }
}
