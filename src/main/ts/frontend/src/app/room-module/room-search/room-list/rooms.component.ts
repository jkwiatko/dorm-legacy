import {Component, OnInit} from '@angular/core';
import {RoomModel} from "../../model/room.model";

@Component({
    selector: 'app-rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {
    longitude = 51.678418;
    latitude = 51.678418;

    rooms: RoomModel[] = [];

    constructor() {
    }

    ngOnInit() {
    }

    onRoomSelected(event: RoomModel) {
        this.longitude = event.position.longitude;
        this.latitude = event.position.latitude;
    }
}
