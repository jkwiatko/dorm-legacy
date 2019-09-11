import {Component, OnInit} from '@angular/core';
import {RoomDto} from '../dto/Room.dto';

@Component({
    selector: 'app-rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {

    rooms: RoomDto[] = [
        {
            id: 1,
            name: 'Pokój Manhattan',
            ownerId: 1
        },
        {
            id: 2,
            name: 'Pokój Retkinia',
            ownerId: 1
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2
        },
        {
            id: 4,
            name: 'Pokój Widzew',
            ownerId: 2
        }];

    constructor() {
    }

    ngOnInit() {
    }

}
