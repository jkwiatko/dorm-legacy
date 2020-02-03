import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {Room} from "../model/room";
import {RoomService} from "../providers/room.service";
import {FormGroup} from "@angular/forms";
import {Profile} from "../../profile-module/model/profile";

@Component({
    selector: 'app-room-edit',
    templateUrl: './room-edit.component.html',
    styleUrls: ['./room-edit.component.scss']
})
export class RoomEditComponent implements OnInit {

    room: Room;
    profile: Profile;
    roomSub: Subscription;
    form: FormGroup = new FormGroup({});

    constructor(private route: ActivatedRoute, private roomCli: RoomService) {
    }

    ngOnInit() {
        // this.room = new Room();
        // this.roomSub = this.roomSub = this.route.params.pipe(
        //     switchMap(params => this.roomCli.fetchRoom(+params['id']))
        // ).subscribe(room => {
        //     this.room = room;
        // });
    }

    onSubmit() {
        console.log(this.form);
    }
}
