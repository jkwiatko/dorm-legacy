/* tslint:disable:member-ordering */
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';
import {ProfileModel} from '../models/profile.model';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';
import {ProfileSearchCriteriaModel} from '../models/profile-search-criteria.model';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    public fetchUserProfile(id: number): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/' + id)
            .pipe(tap(ProfileService.addPictureExt));
    }

    public fetchCurrentUserProfile(): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/edit')
            .pipe(tap(ProfileService.addPictureExt));
    }

    public saveProfile(profile: ProfileModel) {
        return this.http.post<ProfileModel>(environment.api + 'profile/edit', profile)
    }

    public fetchInclinationsOptions(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'profile/characteristics');
    }

    public fetchSearchedUserProfile(criteria: ProfileSearchCriteriaModel) : Observable<ProfilePreviewModel[]> {
        return this.http.post<ProfilePreviewModel[]>(environment.api + `profile/search/roommates/`, criteria)
            .pipe(tap(ProfileService.addPictureExtToPreview));
    }

    private static addPictureExtToPreview(profilePreviews: ProfilePreviewModel[]) {
        profilePreviews.forEach(profilePreview => {
            if (profilePreview.picture) {
                profilePreview.picture.base64String = 'data:image/jpeg;base64,' + profilePreview.picture.base64String;
            }
        })
    }

    private static addPictureExt(profile: ProfileModel): void {
        if (profile.profilePictures) {
            profile.profilePictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
        if (profile.ownedRooms) {
            profile.ownedRooms
                .forEach(room => {
                    if (room.picture) {
                        room.picture.base64String = 'data:image/jpeg;base64,' + room.picture.base64String;
                    }
                });
        }
    }
}
