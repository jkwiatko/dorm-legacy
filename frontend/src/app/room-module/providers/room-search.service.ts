import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RoomPreviewModel} from '../../profile-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';
import {RoomService} from './room.service';

@Injectable({
    providedIn: 'root'
})

export class RoomSearchService {
    lastCriteria = null;

    constructor(private http: HttpClient) {
    }

    fetchSearchedRooms(searchCriteria) {
        this.lastCriteria = searchCriteria;
        return this.http.post<RoomPreviewModel[]>(environment.api + 'room/search/', searchCriteria)
            .pipe(tap(RoomService.addPictureExtToPreview));
    }

    getLastCriteria() : SearchCriteria {
        if(this.lastCriteria) {
            return JSON.parse(JSON.stringify(this.lastCriteria));
        } else return {} as SearchCriteria;
    }
}

export class SearchCriteria {
    constructor(
    public cityName,
    public roomName,
    public startingDate,
    public duration,
    public maxPrice,
    public lookingForUserOffer= false) {
    }
}

