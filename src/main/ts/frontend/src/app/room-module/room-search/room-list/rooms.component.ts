import {Component, OnInit} from '@angular/core';
import {Room} from "../../dto/room";

@Component({
    selector: 'app-rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {
    longitude = 51.678418;
    latitude = 51.678418;

    rooms: Room[] = [
        {
            id: 1,
            name: 'Pokój Manhattan',
            ownerId: 1,
            price: 550,
            position: {
                longitude: 49.629381,
                latitude: 49.629381
            }
        },
        {
            id: 2,
            name: 'Pokój Retkinia',
            ownerId: 1,
            price: 650,
            position: {
                longitude: 50.629381,
                latitude: 50.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 450,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 1100,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 1400,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 350,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 1050,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 3,
            name: 'Pokój Górna',
            ownerId: 2,
            price: 900,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            }
        },
        {
            id: 4,
            name: 'Pokój Widzew',
            ownerId: 2,
            price: 700,
            position: {
                longitude: 42.629381,
                latitude: 49.629381
            }
        }];

    constructor() {
    }

    ngOnInit() {
    }

    onRoomSelected(event: Room) {
        this.longitude = event.position.longitude;
        this.latitude = event.position.latitude;
    }
}
