import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {Profile, ProfileService} from '../providers/profile.service';

@Component({
    selector: 'app-profile-edit',
    templateUrl: './profile-edit.component.html',
    styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {

    profileImg: File;
    startDate = new Date(2000, 0, 1);
    form: FormGroup;
    interests: FormArray;
    inclinations: FormArray;

    constructor(private profileClient: ProfileService) {
    }

    get interestsControl() {
        return (this.form.get('interests') as FormArray).controls;
    }

    get inclinationsControl() {
        return (this.form.get('inclinations') as FormArray).controls;
    }

    ngOnInit() {
        const interests = new FormArray([]);
        const inclinations = new FormArray([]);

        this.form = new FormGroup({
            username: new FormControl(null, Validators.required),
            profileImage: new FormControl(null),
            birthDate: new FormControl(null, Validators.required),
            summary: new FormControl(null, Validators.required),
            gender: new FormControl(null, Validators.required),
            work: new FormControl(null),
            university: new FormControl(null),
            interests,
            inclinations,
            cleaningPolicy : new FormControl(null),
            smokingPolicy : new FormControl(null),
            petPolicy : new FormControl(null),
            guestsPolicy : new FormControl(null)
        });
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
                this.profileImg = event.target.files[0];
                // const reader = new FileReader();
                // reader.onload = () => this.profileImgUrl = (reader.result as ArrayBuffer);
                // reader.readAsArrayBuffer(event.target.files[0]);
            } else {
                //show error message
            }
        }

        this.profileImg.
    }

    onDeleteInterest(i: number) {
        (this.form.get('interests') as FormArray).removeAt(i);
    }

    onDeleteInclination(i: number) {
        (this.form.get('inclinations') as FormArray).removeAt(i);
    }

    onSubmit() {
        console.log(this.form);
        const profile: Profile = {
            username: this.form.get('username').value,
            profileImage: this.form.get('profileImage').value,
            birthDate: this.form.get('birthDate').value,
            summary: this.form.get('summary').value,
            gender: this.form.get('gender').value,
            work: this.form.get('work').value,
            university: this.form.get('university').value,
            interests: this.form.get('interests').value,
            inclinations: this.form.get('inclinations').value,
            cleaningPolicy: this.form.get('cleaningPolicy').value,
            smokingPolicy: this.form.get('smokingPolicy').value,
            petPolicy: this.form.get('petPolicy').value,
            guestsPolicy: this.form.get('guestsPolicy').value,
        };
        this.profileClient.saveProfile(this.profileImg);
    }
}
