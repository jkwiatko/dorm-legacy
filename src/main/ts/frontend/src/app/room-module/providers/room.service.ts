import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RoomModel} from "../model/room.model";
import {environment} from "../../../environments/environment";
import {tap} from "rxjs/operators";
import {CityRoomsModel} from "../../shared-module/models/city-rooms.model";

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    constructor(private http: HttpClient) {
    }

    public fetchRoom(id: number): Observable<RoomModel> {
        console.log(id);
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

    private addPictureExtension2CityRooms(cityRooms: CityRoomsModel) {
        cityRooms.rooms.forEach(dto => {
            if (dto.picture) {
                dto.picture.base64String = 'data:image/jpeg;base64,' + dto.picture.base64String
            }
        });
    }

    fetchRoomsFromCity(city: string): Observable<CityRoomsModel> {
        return this.http.get<CityRoomsModel>(environment.api + 'room/find/' + city)
            .pipe(tap(this.addPictureExtension2CityRooms));
    }

    fetchAvailableCities() : Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'room/cities')
    }

    fetchSearchedRooms(city: string ,value: string) : Observable<CityRoomsModel> {
        return this.http.get<CityRoomsModel>(environment.api + 'room/find/' + city + '/search/' + value)
            .pipe(tap(this.addPictureExtension2CityRooms));
    }
}
