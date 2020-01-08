import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Profile} from '../profile-edit/profile-edit.component';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    constructor(private http: HttpClient) {
    }

    public fetchProfile(id: number): Observable<Profile> {
        return this.http.get<Profile>(environment.api + 'profile/' + id);
    }

    public saveProfile(profile: Profile) {
        this.http.post<Profile>(environment.api + 'profile/edit', profile).subscribe();
    }
}
