import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoomModel} from '../models/room.model';
import {environment} from '../../../environments/environment';
import {map, tap} from 'rxjs/operators';
import {validatorService} from '../../shared-module/lazy-async-validator/lazy-async-validator';
import {ToastrService} from 'ngx-toastr';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';

@Injectable({
    providedIn: 'root'
})
export class RoomService implements validatorService {

    constructor(private http: HttpClient, private toastr: ToastrService) {
    }

    static addPictureExtToPreview(rooms: RoomPreviewModel[]) {
        rooms.forEach(room => {
            if (room.picture) {
                room.picture.base64String = 'data:image/jpeg;base64,' + room.picture.base64String
            }
        });
    }

    static addPictureExt(room: RoomModel): void {
        if (room.pictures) {
            room.pictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
        if (room.owner.profilePictures) {
            room.owner.profilePictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
    }

    public fetchRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id)
            .pipe(tap(RoomService.addPictureExt));
    }

    public createRoom(room: RoomModel) {
        return this.http
            .post<RoomModel>(environment.api + 'room/create', room)
    }

    public editRoom(room: RoomModel): Observable<RoomModel> {
        return this.http
            .post<RoomModel>(environment.api + 'room/edit', room)
    }

    fetchAvailableCities(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'room/cities')
    }

    fetchAvailableAmenities() {
        return this.http.get<string[]>(environment.api + 'room/amenities');
    }

    checkIfNotValid(cityName: string) {
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

    book(id: number) {
        return this.http.patch(environment.api + 'room/book', id);
    }

    unBook(id: number) {
        return this.http.patch(environment.api + 'room/unBook/', id);
    }

    isBooked(id: number) {
        return this.http.get<boolean>(environment.api + 'room/booked/' + id);
    }
}
