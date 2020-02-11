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
        return this.http.get<ProfileModel>(environment.api + 'profile/' + id).pipe(tap(this.addPictureExtension));
    }

    public fetchCurrentUserProfile(): Observable<ProfileModel> {
        return this.http.get<ProfileModel>(environment.api + 'profile/edit').pipe(tap(this.addPictureExtension));
    }

    public saveProfile(profile: ProfileModel) {
        this.http.post<ProfileModel>(environment.api + 'profile/edit', profile).subscribe();
    }

    private addPictureExtension(profile : ProfileModel) : void {
        if(profile.profilePicture) {
            profile.profilePicture.base64String = 'data:image/jpeg;base64,' + profile.profilePicture.base64String
        }
    }
}
