import {Component, OnInit} from '@angular/core';
import {EMPTY} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {RoomModel} from "../model/room.model";
import {RoomService} from "../providers/room.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {switchMap} from "rxjs/operators";
import {ProfileService} from "../../profile-module/providers/profile.service";
import {ProfileModel} from "../../profile-module/model/profile.model";
import {Picture} from "../../shared-module/model/picture.model";

@Component({
    selector: 'app-room-edit',
    templateUrl: './room-edit.component.html',
    styleUrls: ['./room-edit.component.scss']
})
export class RoomEditComponent implements OnInit {

    form: FormGroup;
    profile = new ProfileModel();
    room = new RoomModel();
    editMode = false;

    constructor(private route: ActivatedRoute, private roomCli: RoomService, private profileCli: ProfileService) {
    }

    get amenitiesControl() {
        return (this.form.get('amenities') as FormArray).controls;
    }

    ngOnInit() {
        this.setForm(this.room);

        this.route.url.subscribe(url => {
            if (url[url.length - 1].path === 'create') {
                const sub = this.profileCli.fetchCurrentUserProfile().subscribe(profile => {
                    this.profile = profile;
                    sub.unsubscribe();
                });
            }
        });

        const sub = this.route.params.pipe(
            switchMap(params => +params['id'] ? this.roomCli.fetchCurrentUserRoom(+params['id']) : EMPTY)
        ).subscribe(room => {
            this.room = (new RoomModel().merge(room));
            this.sortPictures(this.room.pictures);
            this.editMode = true;

            this.profile = room.owner;
            this.room.owner = null;

            this.setForm(this.room);
            sub.unsubscribe();
        });

    }

    private setForm(room: RoomModel) {
        const amenities = new FormArray([]);
        for (const amenity of room.amenities) {
            amenities.push(new FormControl(amenity, Validators.required));
        }

        this.form = new FormGroup({
            deposit: new FormControl(room.deposit),
            monthlyPrice: new FormControl(room.monthlyPrice),
            houseArea: new FormControl(room.houseArea),
            roomsNumber: new FormControl(room.roomsNumber),
            address: new FormGroup({
                //can be null :(
                city: new FormControl(room.address.city),
                street: new FormControl(room.address.street),
                number: new FormControl(room.address.number),
            }),
            description: new FormControl(room.description, Validators.required),
            availableFrom: new FormControl(new Date(room.availableFrom)),
            minDuration: new FormControl(room.minDuration),
            amenities: amenities
        });
    }

    onSubmit() {
        if(this.editMode) {
            this.roomCli.editRoom(this.room.merge(this.form.value));
        } else {
            this.roomCli.createRoom(this.room.merge(this.form.value));
        }
    }

    onDeleteAmenity(i: number) {
        (this.form.get('amenities') as FormArray).removeAt(i);
    }

    onAddAmenities() {
        (this.form.get('amenities') as FormArray).push(
            new FormControl(null, Validators.required)
        );
    }

    OnSelectFile(event, number: number) {
        if (event.target.files && event.target.files[0]) {
            let pictureIndex = number;
            if(number > this.room.pictures.length) {
                this.room.pictures.splice(number, 1);
                pictureIndex--;
            }
            let file = event.target.files[0];
            let reader = new FileReader();
            if (file.type.match('image.*')) {
                reader.onload = () => {
                    this.room.pictures[pictureIndex] = {
                        name: file.name,
                        base64String: reader.result.toString(),
                        pictureOrder: pictureIndex
                    };
                };
                reader.readAsDataURL(file);
            }
        }
    }

    private sortPictures(pictures: Picture[]) {
        pictures.sort(((a, b) => {
            if (a.pictureOrder > b.pictureOrder) {
                return 1;
            }
            if (b.pictureOrder > a.pictureOrder) {
                return -1;
            }
            return 0;
        }));
    }
}
