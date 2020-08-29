import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ProfileService} from '../providers/profile.service';
import {Router} from '@angular/router';
import {ProfileModel} from '../models/profile.model';
import {PictureModel} from '../../shared-module/models/picture.model';
import {ToastrService} from 'ngx-toastr';
import {DateParserPipe} from '../../shared-module/pipes/dateParser.pipe';
import * as moment from 'moment';
import {environment} from '../../../environments/environment';
import {AlertController, ViewWillEnter} from '@ionic/angular';
import {navigationAlertFactory} from '../../shared-module/alerts/navigation.alert';

@Component({
    selector: 'app-profile-edit',
    templateUrl: './profile-edit.component.html',
    styleUrls: ['./profile-edit.component.scss']
})

export class ProfileEditComponent implements OnInit, ViewWillEnter {

    startDate = new Date(2000, 0, 1);
    form: FormGroup;
    profile: ProfileModel = new ProfileModel();
    profileImg: PictureModel = new PictureModel();
    characteristicsOptions: string[] = [];
    submitted = false;
    mobile = environment.mobile;

    constructor(private profileClient: ProfileService,
                private router: Router,
                private dateParser: DateParserPipe,
                private alertController: AlertController,
                private toastr: ToastrService,
    ) {
    }

    ngOnInit() {
        this.setForm(new ProfileModel());
    }

    ionViewWillEnter(): void {
        this.profileClient.fetchInclinationsOptions()
            .subscribe((options) => this.characteristicsOptions = options);
        this.profileClient.fetchCurrentUserProfile().subscribe(profile => {
            this.profile = profile;
            this.setForm(profile);
        });
    }

    get interestsControl() {
        return (this.form.get('interests') as FormArray).controls;
    }

    get inclinationsControl() {
        return (this.form.get('inclinations') as FormArray).controls;
    }

    setForm(profile: ProfileModel) {
        const interests = new FormArray([]);
        for (const interest of profile.interests) {
            interests.push(new FormControl(interest, Validators.required));
        }
        const inclinations = new FormArray([]);
        for (const inclination of profile.inclinations) {
            inclinations.push(new FormControl(inclination, Validators.required));
        }

        this.form = new FormGroup({
            firstName: new FormControl(profile.firstName, Validators.required),
            lastName: new FormControl(profile.lastName, Validators.required),
            birthDate: new FormControl(this.dateParser.transform(profile.birthDate), Validators.required),
            description: new FormControl(profile.description, Validators.required),
            gender: new FormControl(profile.gender, Validators.required),
            workingIn: new FormControl(profile.workingIn),
            studyingAt: new FormControl(profile.studyingAt),
            interests,
            inclinations,
            cleaningPolicy: new FormControl(profile.cleaningPolicy, Validators.required),
            smokingPolicy: new FormControl(profile.smokingPolicy, Validators.required),
            petPolicy: new FormControl(profile.petPolicy, Validators.required),
            guestsPolicy: new FormControl(profile.guestsPolicy, Validators.required),
        });
        if (profile.profilePictures && profile.profilePictures[0]) {
            this.profileImg = profile.profilePictures[0];
        }
    }

    onAddInterest() {
        (this.form.get('interests') as FormArray).push(
            new FormControl(null, Validators.required)
        );
    }

    onAddInclination() {
        (this.form.get('inclinations') as FormArray).push(
            new FormControl(null, Validators.required)
        );
    }

    OnSelectFile(event) {
        if (event.target.files && event.target.files[0]) {
            if (event.target.files[0].type.match('image.*')) {
                this.profileImg.name = event.target.files[0].name;
                const reader = new FileReader();
                reader.onload = () => this.profileImg.base64String = reader.result.toString();
                reader.readAsDataURL(event.target.files[0]);
            } else {
                this.toastr.error('Wybrany plik nie jest zdjeciem', 'Zły plik');
            }
        }
    }

    onDeleteInterest(i: number) {
        (this.form.get('interests') as FormArray).removeAt(i);
    }

    onDeleteInclination(i: number) {
        (this.form.get('inclinations') as FormArray).removeAt(i);
    }

    onSubmit() {
        this.submitted = true;
        this.form.markAllAsTouched();
        if (this.form.invalid) {
            this.toastr.error('Prosze wypełnij poprawie wszystkie pola', 'Błędne dane');
        } else {
            const profile: ProfileModel = {
                firstName: this.form.get('firstName').value,
                lastName: this.form.get('lastName').value,
                birthDate: this.form.get('birthDate').value,
                description: this.form.get('description').value,
                gender: this.form.get('gender').value,
                workingIn: this.form.get('workingIn').value,
                studyingAt: this.form.get('studyingAt').value,
                interests: this.form.get('interests').value,
                inclinations: this.form.get('inclinations').value,
                cleaningPolicy: this.form.get('cleaningPolicy').value,
                smokingPolicy: this.form.get('smokingPolicy').value,
                petPolicy: this.form.get('petPolicy').value,
                guestsPolicy: this.form.get('guestsPolicy').value,
                profilePictures: this.profileImg.base64String ? [this.profileImg] : [],
                ownedRooms: null
            };
            this.profileClient.saveProfile(profile).subscribe(() => this.alertController.create({
                message: 'Dane zostały zaakutalizowane',
                header: 'Sukces',
                buttons: ['Oki']
            }).then(alert => alert.present()));
        }
    }

    navigateToRoom(id: number) {
        const editRoomNav = () => { this.router.navigate(['room', id, 'edit']).then(); }
        if (this.form.touched) {
            this.alertController.create(navigationAlertFactory(editRoomNav)).then(alert => alert.present());
        } else {
            editRoomNav();
        }
    }

    onAddRoom() {
        const createRoomNav = () => { this.router.navigate(['/room/create']).then(); }
        if(this.form.touched) {
            this.alertController.create(navigationAlertFactory(createRoomNav)).then(alert => alert.present());
        } else {
            createRoomNav();
        }
    }

    minDate() {
        return moment('1900-01-01').format('YYYY-MM-DD');
    }

    maxDate() {
        return moment(new Date()).subtract(15, 'years').format('YYYY-MM-DD');
    }

}
