import {Component} from '@angular/core';
import {ProfileService} from '../providers/profile.service';
import {ActivatedRoute} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {ProfileModel} from '../models/profile.model';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';
import {RoomService} from '../../room-module/providers/room.service';
import {AlertController, NavController, ViewWillEnter} from '@ionic/angular';

@Component({
    selector: 'app-profile-details',
    templateUrl: './profile-details.component.html',
    styleUrls: ['./profile-details.component.scss']
})
export class ProfileDetailsComponent implements ViewWillEnter {

    constructor(
        private profileService: ProfileService,
        private roomService: RoomService,
        private route: ActivatedRoute,
        private navController: NavController,
        private alertController: AlertController
    ) {
    }

    roomIdToPickRoommate: number
    profile = new ProfileModel();
    profilePreview = new ProfilePreviewModel();

    ionViewWillEnter(): void {
        this.route.params.pipe(
            switchMap(params => {
                this.roomIdToPickRoommate = params.roomId;
                return this.profileService.fetchUserProfile(params.userId);
            })
        ).subscribe(profile => {
            this.profile = profile;
            this.profilePreview = ProfilePreviewModel.buildFromProfileModel(profile);
        });
    }

    pickRoommate() {
        this.roomService.pickRoommate(+this.roomIdToPickRoommate, this.profile.id)
            .subscribe(() => {
                this.alertController.create({
                    message: 'Współokator został pomyślnie dodany do pokoju',
                    header: 'Sukces',
                    buttons: [{text: 'Oki', handler: () => this.navController.back()}]
                }).then(alert => alert.present());
            });
    }

    removeRoommate() {
        this.alertController.create({
            header: 'Uwaga!',
            message: 'Czy na pewno chcesz usunąć współlokatora?',
            buttons: [
                {
                    text: 'Tak',
                    handler: () => this.roomService
                        .removeRoommate(+this.roomIdToPickRoommate, this.profile.id)
                        .subscribe(this.profile.rentedRoomId = null)
                },
                'Nie'
            ]
        }).then(alert => alert.present());
    }
}
