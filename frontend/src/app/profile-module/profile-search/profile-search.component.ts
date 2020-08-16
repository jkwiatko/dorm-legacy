import {Component, OnInit} from '@angular/core';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';
import {ActivatedRoute} from '@angular/router';
import {ProfileService} from '../providers/profile.service';
import {switchMap} from 'rxjs/operators';
import {AbstractControl, FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ProfileSearchCriteriaModel} from '../models/profile-search-criteria.model';

@Component({
    selector: 'app-profile-search',
    templateUrl: './profile-search.component.html',
    styleUrls: ['./profile-search.component.scss']
})
export class ProfileSearchComponent implements OnInit {

    roomId;
    isLoading = false;
    possibleInclinations: string[];
    minimalMaximalValue = 0;
    profiles: ProfilePreviewModel[] = [];

    form = new FormGroup({
        gender: new FormControl(null, []),
        minAge: new FormControl(null, Validators.min(0)),
        maxAge: new FormControl(
            null,
            (control: AbstractControl) => Validators.min(this.minimalMaximalValue)(control)
        ),
        workAndUniversity: new FormControl(null, []),
        inclinations: new FormArray([])
    });

    constructor(private route: ActivatedRoute,
                private profileService: ProfileService) {
    }

    get inclinationsControl() {
        return (this.form.get('inclinations') as FormArray).controls;
    }

    ngOnInit(): void {
        this.route.params.pipe(
            switchMap(params => {
                this.roomId = params.id;
                return this.profileService.fetchUserProfilePreviewsForRoom(params.id);
            }))
            .subscribe(profiles => this.profiles = profiles);
        this.form.valueChanges.subscribe(value => this.submit(value));
    }

    reset() {
        this.form.reset();
    }

    submit(value: ProfileSearchCriteriaModel) {
        value.roomId = this.roomId;
        this.profileService.fetchSearchedUserProfile(value).subscribe(profiles => this.profiles = profiles);
    }

    onAddInclination() {
        (this.form.get('inclinations') as FormArray)
            .push(new FormControl(null, Validators.required))
    }

    onDeleteInclination(i: number) {
        (this.form.get('inclinations') as FormArray).removeAt(i);
    }

    onMinChange() {
        this.minimalMaximalValue = this.form.get('minAge').value;
        this.form.get('maxAge').updateValueAndValidity();
    }
}
