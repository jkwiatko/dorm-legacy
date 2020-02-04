import {Component, OnInit} from '@angular/core';
import {Room} from "../../model/room";
import {Profile} from "../../../profile-module/model/profile";

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
            owner: new Profile(),
            monthlyPrice: 550,
            position: {
                longitude: 49.629381,
                latitude: 49.629381
            },
            availableFrom : '',
            minDuration: 5,
            amenities: [],
            pictures: [],
            address: {},
            deposit: 15,
            description: ''
        },
        {
            id: 2,
            name: 'Pokój Retkinia',
            owner: new Profile(),
            monthlyPrice: 650,
            position: {
                longitude: 50.629381,
                latitude: 50.629381
            },
            availableFrom : '',
            minDuration: 5,
            amenities: [],
            pictures: [],
            address: {},
            deposit: 15,
            description: ''
        },
        {
            id: 3,
            name: 'Pokój Górna',
            owner: new Profile(),
            monthlyPrice: 450,
            position: {
                longitude: 53.629381,
                latitude: 56.629381
            },
            availableFrom : '',
            minDuration: 5,
            amenities: [],
            pictures: [],
            address: {},
            deposit: 15,
            description: ''
        },
        ];

    constructor() {
    }

    ngOnInit() {
    }

    onRoomSelected(event: Room) {
        this.longitude = event.position.longitude;
        this.latitude = event.position.latitude;
    }
}
