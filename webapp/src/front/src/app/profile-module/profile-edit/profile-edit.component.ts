import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-profile-edit',
    templateUrl: './profile-edit.component.html',
    styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {

    profileImgUrl = null;
    startDate = new Date(2000, 0, 1);
    form: FormGroup;
    interests: FormArray;
    inclinations: FormArray;

    constructor() {
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
            'username': new FormControl(null, Validators.required),
            'profile-image': new FormControl(null),
            'birthDate': new FormControl(null, Validators.required),
            'summary': new FormControl(null, Validators.required),
            'gender': new FormControl(null, Validators.required),
            'work': new FormControl(null),
            'university': new FormControl(null),
            'interests': interests,
            'inclinations': inclinations,
            'cleaningPolicy' : new FormControl(null),
            'smokingPolicy' : new FormControl(null),
            'petPolicy' : new FormControl(null),
            'guestsPolicy' : new FormControl(null)
        });
    }

    onAddInterest() {
        (<FormArray>this.form.get('interests')).push(
            new FormControl(null, Validators.required)

        );
    }

    onAddInclination() {
        (<FormArray>this.form.get('inclinations')).push(
            new FormControl(null, Validators.required)

        )
    }


    OnSelectFile(event) {
        if (event.target.files && event.target.files[0]) {
            if (event.target.files[0].type.match('image.*')) {
                let reader = new FileReader();
                reader.onload = () => this.profileImgUrl = reader.result;
                reader.readAsDataURL(event.target.files[0]);
            } else {
                //show error message
            }
        }
    }

    onDeleteInterest(i: number) {
        (<FormArray>this.form.get('interests')).removeAt(i);
    }

    onDeleteInclination(i: number) {
        (<FormArray>this.form.get('inclinations')).removeAt(i);
    }

    onSubmit() {
        console.log(this.form);
    }
}
