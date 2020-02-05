import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {Observable} from "rxjs";
import {RoomModel} from "../model/room.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

    fetchRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id).pipe(
            tap(room => {
                this.authorizePictureUrl(room);
            }));
    }

    createRoom(roomModel: RoomModel) {
        console.log(roomModel);
        this.http.post<RoomModel>(environment.api + 'room/create', roomModel).subscribe();
    }

    private authorizePictureUrl(room: RoomModel) {
        if(room.pictures) {
            room.pictures[0].base64String = this.sanitizer.bypassSecurityTrustUrl(
                'data:image/jpeg;base64,' + room.pictures[0].base64String
            )
        }
    }
}
