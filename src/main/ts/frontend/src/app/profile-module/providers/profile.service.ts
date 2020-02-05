import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from "rxjs/operators";
import {DomSanitizer} from "@angular/platform-browser";
import {ProfileModel} from "../model/profile.model";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient, private sanitizer: DomSanitizer) {
    }

    fetchUserProfile(id: number): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/' + id).pipe(
            tap(profile => {
                this.authorizePictureUrl(profile);
        }));
    }

    public fetchCurrentUserProfile(): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/edit').pipe(
            tap(profile => {
                this.authorizePictureUrl(profile);
            }));
    }

    public saveProfile(profile: ProfileModel) {
        this.http.post<ProfileModel>(environment.api + 'profile/edit', profile).subscribe();
    }

    private authorizePictureUrl(profile: ProfileModel) {
        if(profile.profilePicture) {
            profile.profilePicture.base64String = this.sanitizer.bypassSecurityTrustUrl(
                'data:image/jpeg;base64,' + profile.profilePicture.base64String
            )
        }
    }
}
