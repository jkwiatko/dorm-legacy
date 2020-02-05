import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {RoomModel} from "../../model/room.model";

@Component({
    selector: 'app-room',
    templateUrl: './room.component.html',
    styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

    @Input() room: RoomModel;
    @Output() roomSelected = new EventEmitter<RoomModel>();

    constructor() {
    }

    ngOnInit() {
    }

    @HostListener('click')
    onClick() {
        this.roomSelected.emit(this.room);
    }
}
