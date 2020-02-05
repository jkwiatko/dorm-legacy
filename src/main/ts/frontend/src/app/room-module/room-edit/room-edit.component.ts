import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {RoomModel} from "../model/room.model";
import {RoomService} from "../providers/room.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfileModel} from "../../profile-module/model/profile.model";

@Component({
    selector: 'app-room-edit',
    templateUrl: './room-edit.component.html',
    styleUrls: ['./room-edit.component.scss']
})
export class RoomEditComponent implements OnInit {

    room: RoomModel;
    profile: ProfileModel;
    roomSub: Subscription;
    form: FormGroup;

    constructor(private route: ActivatedRoute, private roomCli: RoomService) {
    }

    get amenitiesControl() {
        return (this.form.get('amenities') as FormArray).controls;
    }

    ngOnInit() {
        this.room = new RoomModel();
        this.profile = new ProfileModel();
        this.setForm();
    }

    private setForm() {
        const amenities = new FormArray([]);
        for (const amenity of this.room.amenities) {
            amenities.push(new FormControl(amenity, Validators.required));
        }

        this.form = new FormGroup({
            deposit: new FormControl(this.room.deposit),
            monthlyPrice: new FormControl(this.room.monthlyPrice),
            room: new FormControl(this.room.room),
            roomNumber: new FormControl(this.room.roomNumber),
            city: new FormControl(this.room.address.city),
            street: new FormControl(this.room.address.street),
            streetNumber: new FormControl(this.room.address.number),
            description: new FormControl(this.room.description, Validators.required),
            availableFrom : new FormControl(this.room.availableFrom),
            minDuration: new FormControl(this.room.minDuration),
            amenities: amenities
        });
    }

    onSubmit() {
        this.roomCli.saveRoom(new RoomModel(this.form.value));
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
