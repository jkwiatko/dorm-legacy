import {Component, OnInit} from '@angular/core';
import {EMPTY, Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {RoomModel} from "../model/room.model";
import {RoomService} from "../providers/room.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {switchMap} from "rxjs/operators";
import {ProfileService} from "../../profile-module/providers/profile.service";

@Component({
    selector: 'app-room-edit',
    templateUrl: './room-edit.component.html',
    styleUrls: ['./room-edit.component.scss']
})
export class RoomEditComponent implements OnInit {

    room: RoomModel;
    roomSub: Subscription;
    form: FormGroup;

    constructor(private route: ActivatedRoute, private roomCli: RoomService, private profileCli: ProfileService) {
    }

    get amenitiesControl() {
        return (this.form.get('amenities') as FormArray).controls;
    }

    ngOnInit() {
       this.room = new RoomModel();

       this.route.url.subscribe(url => {
          if(url[url.length - 1].path === 'create') {
              const sub = this.profileCli.fetchCurrentUserProfile().subscribe(profile => {
                  this.room.owner = profile;
                  sub.unsubscribe();
              });
          }
       });

       this.roomSub = this.route.params.pipe(
            switchMap(params => +params['id'] ? this.roomCli.fetchCurrentUserRoom(+params['id']) : EMPTY)
        ).subscribe(room => this.room = room);
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
            roomsNumber: new FormControl(this.room.roomsNumber),
            address: new FormGroup({
                city: new FormControl(this.room.address.city),
                street: new FormControl(this.room.address.street),
                number: new FormControl(this.room.address.number),
            }),
            description: new FormControl(this.room.description, Validators.required),
            availableFrom : new FormControl(this.room.availableFrom),
            minDuration: new FormControl(this.room.minDuration),
            amenities: amenities
        });
    }

    onSubmit() {
        let room = new RoomModel(this.form.value);
        room.owner = this.room.owner;
        this.roomCli.createRoom(room);
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
