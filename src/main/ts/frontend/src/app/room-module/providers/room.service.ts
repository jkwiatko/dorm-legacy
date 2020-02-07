import {Injectable, SecurityContext} from '@angular/core';
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

  constructor(private http: HttpClient, private sanitizer: DomSanitizer, private profileService: ProfileService) { }

    public fetchCurrentUserRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id)
            .pipe(tap(room => this.trustReceivedPicturesUrl(room)));
    }

    public createRoom(room: RoomModel) {
        this.authorizeSentPictureUrls(room);
        this.http.post<RoomModel>(environment.api + 'room/create', room).subscribe();
    }

    private trustReceivedPicturesUrl(room: RoomModel) {
      if(room.owner) {
          this.profileService.trustReceivedPictureUrl(room.owner);
      }
      if(room.pictures) {
          room.pictures.forEach(picture => picture.base64String = this.sanitizer
              .bypassSecurityTrustUrl('data:image/jpeg;base64,' + picture.base64String))
      }
    }

    public authorizeSentPictureUrls(room: RoomModel) {
        room.pictures.forEach(picture => picture.base64String = this.sanitizer
            .sanitize(SecurityContext.URL, 'data:image/jpeg;base64,' + room.owner.profilePicture.base64String));
    }
}
