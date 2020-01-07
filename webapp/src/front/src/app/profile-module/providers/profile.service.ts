import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";

interface Profile {

}

@Injectable({
    providedIn: 'root'
})
export class ProfileService {

    profileFetchAPI: string = "lol";
    profileSaveAPI: string = "lol";

    constructor(private http: HttpClient) {
    }

    public fetchProfile(id: number): Observable<Profile> {
        return this.http.get<Profile>(this.profileFetchAPI);
    }

    public saveProfile(profile: Profile) {
        this.http.put<Profile[]>(this.profileSaveAPI, profile).subscribe();
    }
}
