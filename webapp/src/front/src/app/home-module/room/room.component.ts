import {Component, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {RoomDto} from '../dto/Room.dto';

@Component({
    selector: 'app-room',
    templateUrl: './room.component.html',
    styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

    @Input() room: RoomDto;
    @Output() roomSelected = new EventEmitter<RoomDto>();

    constructor() {
    }

    ngOnInit() {
    }

    @HostListener('click')
    onClick() {
        this.roomSelected.emit(this.room);
    }
}
