import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';
import {RoomService} from './room.service';

@Injectable({
    providedIn: 'root'
})

export class RoomSearchService {

    constructor(private http: HttpClient) {
    }

    fetchSearchedRooms(searchCriteria) {
        return this.http.post<RoomPreviewModel[]>(environment.api + 'room/search/', searchCriteria)
            .pipe(tap(RoomService.addPictureExtToPreview));
    }
}

