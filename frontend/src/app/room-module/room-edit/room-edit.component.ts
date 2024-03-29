import {Component, OnInit} from '@angular/core';
import {EMPTY} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {RoomModel} from '../models/room.model';
import {RoomService} from '../providers/room.service';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {switchMap} from 'rxjs/operators';
import {ProfileService} from '../../profile-module/providers/profile.service';
import {PictureModel} from '../../shared-module/models/picture.model';
import {ToastrService} from 'ngx-toastr';
import {DateParserPipe} from '../../shared-module/pipes/dateParser.pipe';
import {LazyAsyncValidatorFactory,} from '../../shared-module/lazy-async-validator/lazy-async-validator';
import {environment} from '../../../environments/environment';
import * as moment from 'moment';
import {LoadingController, NavController} from '@ionic/angular';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';


@Component({
    selector: 'app-room-edit',
    templateUrl: './room-edit.component.html',
    styleUrls: ['./room-edit.component.scss']
})
export class RoomEditComponent implements OnInit {

    get amenitiesControl() {
        return (this.form.get('amenities') as FormArray).controls;
    }


    constructor
    (
        private route: ActivatedRoute,
        private nav: NavController,
        private roomCli: RoomService,
        private profileCli: ProfileService,
        private dateParser: DateParserPipe,
        private toastr: ToastrService,
        private loaderCtrl: LoadingController
    ) {
    }

    form: FormGroup;
    profile = new ProfilePreviewModel();
    room = new RoomModel();
    amenityOptions: string[] = [];
    editMode = false;
    submitted = false;
    mobile = environment.mobile;
    loader;

    private static sortPictures(pictures: PictureModel[]) {
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

    minDate() {
        return moment(new Date()).format('YYYY-MM-DD');
    }

    maxDate() {
        return moment(new Date()).add(5, 'years').format('YYYY-MM-DD');
    }

    ngOnInit() {
        this.setForm(this.room);
        this.loaderCtrl.create({keyboardClose: true, message: 'Saving room...'}).then(loader => this.loader = loader);
        this.roomCli.fetchAvailableAmenities()
            .subscribe((amenityOptions) => this.amenityOptions = amenityOptions);

        this.route.url.subscribe(url => {
            if (url[url.length - 1].path === 'create') {
                this.profileCli.fetchCurrentUserProfile()
                    .subscribe(profile => this.profile = ProfilePreviewModel.buildFromProfileModel(profile));
            }
        });
        this.route.params.pipe(
            switchMap(params => +params.id ? this.roomCli.fetchRoom(+params.id) : EMPTY)
        ).subscribe(room => {
            this.room = (new RoomModel().merge(room));
            RoomEditComponent.sortPictures(this.room.pictures);
            this.editMode = true;
            this.profile = ProfilePreviewModel.buildFromProfileModel(room.owner);
            this.setForm(this.room);
        });

    }

    private setForm(room: RoomModel) {
        const amenities = new FormArray([]);
        for (const amenity of room.amenities) {
            amenities.push(new FormControl(amenity, Validators.required));
        }

        this.form = new FormGroup({
            name: new FormControl(room.name, Validators.required),
            deposit: new FormControl(room.deposit, [Validators.required, Validators.min(1)]),
            monthlyPrice: new FormControl(
                room.monthlyPrice,
                [Validators.required, Validators.min(1)]),
            houseArea: new FormControl(room.houseArea, [Validators.required, Validators.min(1)]),
            roomsNumber: new FormControl(room.roomsNumber, [Validators.required, Validators.min(1)]),
            address: new FormGroup({
                // can be null
                city: new FormControl(
                    room.address.city,
                    Validators.required,
                    LazyAsyncValidatorFactory(this.roomCli)
                ),
                street: new FormControl(room.address.street, Validators.required),
                number: new FormControl(room.address.number, Validators.required),
            }),
            description: new FormControl(room.description, Validators.required),
            availableFrom: new FormControl(this.dateParser.transform(this.room.availableFrom), Validators.required),
            minDuration: new FormControl(room.minDuration, [Validators.required, Validators.min(1)]),
            amenities
        });
    }

    onSubmit() {
        if(this.form.valid) {
            this.loader.present()
            this.submitted = true;
            this.form.markAllAsTouched();
            this.room.pictures = this.room.pictures.filter((el) => el != null);

            if (this.form.invalid) {
                this.toastr.error('Prosze wypełnij poprawie wszystkie pola', 'Błędne dane');
            } else {
                const roomModel = this.room.merge(this.form.value)
                const obs = this.editMode ? this.roomCli.editRoom(roomModel) : this.roomCli.createRoom(roomModel);
                obs.subscribe(
                    () => {
                        this.loader.dismiss();
                        this.nav.navigateBack(['/profile/edit']).then()
                    },
                    error => {
                        this.loader.dismiss()
                        this.toastr.error(error.error.message, 'Błędne dane!');
                    });
            }
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

    OnSelectFile(event, index: number) {
        if (event.target.files && event.target.files[0]) {
            let pictureIndex = index;
            if (index > this.room.pictures.length) {
                this.room.pictures.splice(index, 1);
                pictureIndex--;
            }
            const file = event.target.files[0];
            const reader = new FileReader();
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
}
