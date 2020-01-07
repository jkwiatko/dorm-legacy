import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

export interface Profile {
    username: string;
    profileImage: string;
    birthDate: string;
    summary: string;
    gender: string;
    work: string;
    university: string;
    interests: [string];
    inclinations: [string];
    cleaningPolicy: string;
    smokingPolicy: string;
    petPolicy: string;
    guestsPolicy: string;
}

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
        const formData = new FormData();
        formData.append('image', profile.profileImage);
        this.http.post<>(environment.api + 'profile/edit', formData).subscribe();
    }

    public WORKINGsaveProfile(file: File) {
        let formData = new FormData();
        formData.append('image', file);
        console.log(formData.get('image'));
        this.http.post(environment.api + 'profile/edit', formData).subscribe();
    }
}
