import {Component, Input, OnInit} from '@angular/core';
import {RoomDto} from "../dto/Room.dto";

@Component({
    selector: 'app-room',
    templateUrl: './room.component.html',
    styleUrls: ['./room.component.scss']
})
export class RoomComponent implements OnInit {

    @Input() room: RoomDto;

    constructor() {
    }

    ngOnInit() {
    }

}
