import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileDetailsComponent} from './profile-details/profile-details.component';
import {ProfileEditComponent} from './profile-edit/profile-edit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '../shared-module/shared.module';
import {AuthGuardService} from '../auth-module/providers/auth-guard.service';
import {DateParserPipe} from '../shared-module/pipes/dateParser.pipe';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {IonicModule} from '@ionic/angular';
import {RoommateSearchComponent} from './roommate-search/roommate-search.component';

const routes: Routes = [
    {path: 'profile/edit', component: ProfileEditComponent, canActivate: [AuthGuardService]},
    {path: 'profile/:id', component: ProfileDetailsComponent, canActivate: [AuthGuardService]},
    {path: 'room/:id/search/roommate', component: RoommateSearchComponent, canActivate: [AuthGuardService]}
];

@NgModule({
    declarations: [ProfileDetailsComponent, ProfileEditComponent, RoommateSearchComponent],
    exports: [],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatInputModule,
        MatRadioModule,
        SharedModule,
        IonicModule,
        FormsModule
    ],
    providers: [DateParserPipe]
})
export class ProfileModule {
}
