import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {Observable} from "rxjs";
import {RoomModel} from "../model/room.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";
import {ProfileService} from "../../profile-module/providers/profile.service";

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    constructor(private http: HttpClient, private sanitizer: DomSanitizer, private profileService: ProfileService) {
    }

    public fetchCurrentUserRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id).pipe(tap(this.addPictureExtension));
    }

    public createRoom(room: RoomModel) {
        this.http
            .post<RoomModel>(environment.api + 'room/create', room)
            .subscribe();
    }

    private addPictureExtension(room : RoomModel) : void {
        room.pictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        room.owner.profilePicture.base64String =  'data:image/jpeg;base64,' + room.owner.profilePicture.base64String;
    }
}
