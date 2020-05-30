import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';
import {ProfileModel} from '../models/profile.model';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    fetchUserProfile(id: number): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/' + id).pipe(tap(this.addPictureExtension));
    }

    public fetchCurrentUserProfile(): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/edit').pipe(tap(this.addPictureExtension));
    }

    public saveProfile(profile: ProfileModel) {
        this.http.post<ProfileModel>(environment.api + 'profile/edit', profile).subscribe();
    }

    private addPictureExtension(profile: ProfileModel): void {
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

    fetchCharacteristicsOptions(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'profile/characteristics');
    }
}
