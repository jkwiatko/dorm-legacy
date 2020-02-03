import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {Room} from "../model/room";
import {RoomService} from "../providers/room.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
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
    form: FormGroup;

    constructor(private route: ActivatedRoute, private roomCli: RoomService) {
    }

    get amenitiesControl() {
        return (this.form.get('amenities') as FormArray).controls;
    }

    ngOnInit() {
        this.profile = new Profile();
        this.room = new Room();
        this.setForm()
        // this.roomSub = this.roomSub = this.route.params.pipe(
        //     switchMap(params => this.roomCli.fetchRoom(+params['id']))
        // ).subscribe(room => {
        //     this.room = room;
        // });
    }

    private setForm() {
        const amenities = new FormArray([]);
        for (const amenity of this.room.amenities) {
            amenities.push(new FormControl(amenity, Validators.required));
        }

        this.form = new FormGroup({
            description: new FormControl(this.room.description, Validators.required),
            availableFrom : new FormControl(this.room.availableFrom),
            minDuration: new FormControl(this.room.minDuration),
            amenities: amenities
        });
    }

    onSubmit() {
        console.log(this.form);
    }

    onDeleteAmenity(i: number) {
        (this.form.get('amenities') as FormArray).removeAt(i);
    }

    onAddAmenities() {
        (this.form.get('amenities') as FormArray).push(
            new FormControl(null, Validators.required)
        );
    }
}
