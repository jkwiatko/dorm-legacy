import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoomModel} from '../model/room.model';
import {environment} from '../../../environments/environment';
import {map, tap} from 'rxjs/operators';
import {CityRoomsModel} from '../../shared-module/models/city-rooms.model';
import {validatorService} from '../../shared-module/lazy-async-validator/lazy-async-validator';
import {ToastrService} from 'ngx-toastr';

@Injectable({
    providedIn: 'root'
})
export class RoomService implements validatorService {

    constructor(private http: HttpClient, private toastr: ToastrService) {
    }

    public fetchRoom(id: number): Observable<RoomModel> {
        return this.http.get<RoomModel>(environment.api + 'room/' + id).pipe(tap(this.addPictureExtension));
    }

    public createRoom(room: RoomModel) {
        return this.http
            .post<RoomModel>(environment.api + 'room/create', room)
    }

    public editRoom(room: RoomModel): Observable<RoomModel> {
        return this.http
            .post<RoomModel>(environment.api + 'room/edit', room)
    }

    private addPictureExtension(room: RoomModel): void {
        if (room.pictures) {
            room.pictures.forEach(img => img.base64String = 'data:image/jpeg;base64,' + img.base64String);
        }
        if (room.owner.profilePictures) {
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

    fetchAvailableCities(): Observable<string[]> {
        return this.http.get<string[]>(environment.api + 'room/cities')
    }

    fetchSearchedRooms(searchCriteria): Observable<CityRoomsModel> {
        console.log(searchCriteria);
        return this.http.post<CityRoomsModel>(environment.api + 'room/search/', searchCriteria)
            .pipe(tap(this.addPictureExtension2CityRooms));
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
}
