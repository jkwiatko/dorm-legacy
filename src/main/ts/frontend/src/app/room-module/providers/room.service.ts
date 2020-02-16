import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RoomModel} from "../model/room.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    constructor(private http: HttpClient) {
    }

    public fetchCurrentUserRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id).pipe(tap(this.addPictureExtension));
    }

    public createRoom(room: RoomModel) {
        return  this.http
            .post<RoomModel>(environment.api + 'room/create', room)
    }

    public editRoom(room: RoomModel) : Observable<RoomModel> {
        return this.http
            .post<RoomModel>(environment.api + 'room/edit', room)
    }

    private addPictureExtension(room : RoomModel) : void {
        if(room.pictures) {
            room.pictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
        if(room.owner.profilePictures) {
            room.owner.profilePictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
    }
}
