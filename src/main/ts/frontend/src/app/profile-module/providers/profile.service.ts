import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from "rxjs/operators";
import {DomSanitizer} from "@angular/platform-browser";
import {Profile} from "../dto/profile";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient, private sanitizer: DomSanitizer) {
    }

    fetchUserProfile(id: number): Observable<Profile> {
        return this.http.get<Profile>(environment.api + 'profile/' + id).pipe(
            tap(profile => {
                this.authorizePictureUrl(profile);
        }));
    }

    public fetchCurrentUserProfile(): Observable<Profile> {
        return this.http.get<Profile>(environment.api + 'profile/edit').pipe(
            tap(profile => {
                this.authorizePictureUrl(profile);
            }));
    }

    public saveProfile(profile: Profile) {
        this.http.post<Profile>(environment.api + 'profile/edit', profile).subscribe();
    }

    private authorizePictureUrl(profile: Profile) {
        if(profile.profilePicture) {
            profile.profilePicture.base64String = this.sanitizer.bypassSecurityTrustUrl(
                'data:image/jpeg;base64,' + profile.profilePicture.base64String
            )
        }
    }
}
