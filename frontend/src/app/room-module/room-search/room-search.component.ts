import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, switchMap, tap} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';
import {RoomPreviewModel} from '../../profile-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {RoomSearchService, SearchCriteria} from '../providers/room-search.service';

@Component({
    selector: 'app-rooms',
    templateUrl: './room-search.component.html',
    styleUrls: ['./room-search.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RoomSearchComponent implements OnInit, OnDestroy {

    constructor(
        private roomSearchService: RoomSearchService,
        private roomService: RoomService,
        private router: Router
    ) {
    }

    rooms: RoomPreviewModel[] = []
    availableCities: string[] = [];
    searchObservable = new Subject<Event>();
    isLoading = false;
    startDate = new Date();
    lastCriteria: SearchCriteria;

    @ViewChild('searchForm', {static: true})
    searchForm: NgForm;
    mobile = environment.mobile;

    ngOnInit() {
        this.lastCriteria = this.roomSearchService.getLastCriteria();
        this.roomService.fetchAvailableCities().subscribe((cities => this.availableCities = cities));
        this.searchObservable.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            debounceTime(500),
            switchMap(() => {
                this.searchForm.form.markAsTouched();
                this.isLoading = true;
                return this.roomSearchService.fetchSearchedRooms(this.searchForm.value);
            })
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
