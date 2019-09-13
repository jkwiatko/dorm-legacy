import {Component, OnInit} from '@angular/core';
import {RoomDto} from '../dto/Room.dto';

@Component({
    selector: 'app-rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {
    longitude = 51.678418;
    latitude = 51.678418;

    rooms: RoomDto[] = [
        {
            id: 1,
            name: 'Pokój Manhattan',
            ownerId: 1,
            position: {
                longitude: 49.629381,
                latitude: 49.629381
            }
        },
        {
            id: 2,
            name: 'Pokój Retkinia',
            ownerId: 1,
            position: {
                longitude: 50.629381,
                latitude: 50.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 4,
            name: 'Pokój Widzew',
            ownerId: 2,
            position: {
                longitude: 42.629381,
                latitude: 49.629381
            }
        }];

    constructor() {
    }

    ngOnInit() {
    }

    onRoomSelected(event: RoomDto) {
        this.longitude = event.position.longitude;
        this.latitude = event.position.latitude;
    }
}
