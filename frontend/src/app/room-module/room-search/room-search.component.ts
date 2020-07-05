import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';
import {RoomPreviewModel} from '../../profile-module/models/room-preview.model';

@Component({
    selector: 'app-rooms',
    templateUrl: './room-search.component.html',
    styleUrls: ['./room-search.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RoomSearchComponent implements OnInit, OnDestroy {

    rooms: RoomPreviewModel[] = []
    availableCities: string[] = [];
    searchObservable = new Subject<Event>();
    isLoading = false;

    @ViewChild('searchForm', {static: true})
    searchForm: NgForm;

    constructor(private roomService: RoomService, private router: Router) {
    }

    ngOnInit() {
        this.roomService.fetchAvailableCities().subscribe((cities => this.availableCities = cities));
        this.searchObservable.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            tap(() => this.isLoading = true),
            debounceTime(500),
            switchMap(() => this.roomService.fetchSearchedRooms(this.searchForm.value))
        ).subscribe(
            rooms => {
                this.rooms = rooms;
                this.isLoading = false;
            },
            console.log
        );
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

    ngOnDestroy() {
        this.searchObservable.unsubscribe();
    }

}
