import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {CityRoomsModel} from '../../shared-module/models/city-rooms.model';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';

@Component({
    selector: 'app-rooms',
    templateUrl: './room-search.component.html',
    styleUrls: ['./room-search.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RoomSearchComponent implements OnInit {

    cityRooms = new CityRoomsModel();
    availableCities: string[] = [];
    searchObservable = new Subject<Event>();
    isLoading = false;

    @ViewChild('f', {static: true})
    form: NgForm;

    constructor(private roomService: RoomService, private router: Router) {
    }

    ngOnInit() {
        this.roomService.fetchAvailableCities().subscribe((cities => this.availableCities = cities));
        this.searchObservable.pipe(
            tap(() => this.isLoading = true),
            debounceTime(1000),
            distinctUntilChanged(),
            switchMap(event => this.roomService.fetchSearchedRooms(this.form.value))
        ).subscribe(cityRooms => {
            this.isLoading = false;
            this.cityRooms = cityRooms;
        });
    }

    navigateToRoom(id: number) {
        this.router.navigate(['room/', id])
    }

    minDate() {
        return moment(new Date()).format('YYYY-MM-DD');
    }

    maxDate() {
        return moment(new Date()).add(5, 'years').format('YYYY-MM-DD');
    }
}
