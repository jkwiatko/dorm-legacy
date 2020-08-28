/* tslint:disable:member-ordering */
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoomModel} from '../models/room.model';
import {environment} from '../../../environments/environment';
import {map, tap} from 'rxjs/operators';
import {ValidatorService} from '../../shared-module/lazy-async-validator/lazy-async-validator';
import {ToastrService} from 'ngx-toastr';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';

@Injectable({
    providedIn: 'root'
})
export class RoomService implements ValidatorService {

    constructor(private http: HttpClient, private toastr: ToastrService) {
    }

    public fetchAvailableCities(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'room/cities')
    }

    public fetchAvailableAmenities(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'room/amenities');
    }

    public fetchRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id)
            .pipe(tap(RoomService.addPictureExt));
    }

    public fetchSearchedRooms(searchCriteria) {
        return this.http.post<RoomPreviewModel[]>(environment.api + 'room/search/', searchCriteria)
            .pipe(tap(RoomService.addPictureExtToPreview));
    }

    public createRoom(room: RoomModel): Observable<RoomModel> {
        return this.http.post<RoomModel>(environment.api + 'room/create', room)
    }

    public editRoom(room: RoomModel): Observable<RoomModel> {
        return this.http.post<RoomModel>(environment.api + 'room/edit', room)
    }

    public checkIfNotValid(cityName: string) {
        return this.fetchAvailableCities().pipe(
            map(availableCities => availableCities.filter(availableCity => availableCity === cityName)),
            map(availableCities => {
                if (!availableCities.length) {
                    this.toastr.error('Niestety to miasto nie jest jeszcze dostępne', 'Miasto niedostępne', {timeOut: 1500});
                }
                return !availableCities.length
            })
        )
    }

    // booking
    public book(id: number) {
        return this.http.patch(environment.api + 'room/book', id);
    }

    public unBook(id: number) {
        return this.http.patch(environment.api + 'room/unBook/', id);
    }

    public isBooked(id: number) {
        return this.http.get<boolean>(environment.api + 'room/booked/' + id);
    }

    public pickRoommate(roomId: number, userId: number) {
        return this.http.put(environment.api + `room/pick/roommate/`,{roomId, userId})
    }

    private static addPictureExtToPreview(rooms: RoomPreviewModel[]) {
        rooms.forEach(room => {
            if (room.picture) {
                room.picture.base64String = 'data:image/jpeg;base64,' + room.picture.base64String
            }
        });
    }

    private static addPictureExt(room: RoomModel): void {
        if (room.pictures) {
            room.pictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
        if (room.owner.profilePictures) {
            room.owner.profilePictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
    }

    removeRoommate(roomId: number,userId: number) {
        return this.http.patch(environment.api + 'room/remove/roommate', {roomId, userId});
    }
}
