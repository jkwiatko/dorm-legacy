import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {Room} from "../../model/room";

@Component({
    selector: 'app-room',
    templateUrl: './room.component.html',
    styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

    @Input() room: Room;
    @Output() roomSelected = new EventEmitter<Room>();

    constructor() {
    }

    ngOnInit() {
    }

    @HostListener('click')
    onClick() {
        this.roomSelected.emit(this.room);
    }
}
