import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

    fetchRoom(id: number): Observable<Room> {
        return this.http.get<Room>(environment.api + 'room/' + id).pipe(
            tap(room => {
                this.authorizePictureUrl(room);
            }));
    }

    private authorizePictureUrl(room: Room) {
        if(room.pictures) {
            room.pictures[0].base64String = this.sanitizer.bypassSecurityTrustUrl(
                'data:image/jpeg;base64,' + room.pictures[0].base64String
            )
        }
    }
}
