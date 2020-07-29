import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {take} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {RoomSearchService, SearchCriteria} from '../providers/room-search.service';
import {SearchType} from '../../shared-module/models/searchType.model';

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
        private router: Router,
        private route: ActivatedRoute
    ) {
    }

    rooms: RoomPreviewModel[] = []
    availableCities: string[] = [];
    searchObservable = new Subject<Event>();
    isLoading = false;
    startDate = new Date();
    lastCriteria: SearchCriteria;
    searchType: SearchType;

    @ViewChild('searchForm', {static: true})
    searchForm: NgForm;
    mobile = environment.mobile;

    minDate() {
        return moment(new Date()).format('YYYY-MM-DD');
    }

    maxDate() {
        return moment(new Date()).add(5, 'years').format('YYYY-MM-DD');
    }

    ngOnInit() {
        this.lastCriteria = this.roomSearchService.getLastCriteria();
        this.route.url.subscribe(this.setSearchType.bind(this));
        this.roomService.fetchAvailableCities()
            .pipe(take(1))
            .subscribe((cities => this.availableCities = cities));
    }

    setSearchType(url) {
        switch (url[url.length - 1].path) {
            case 'own':
                this.searchType = SearchType.OWN_OFFER;
                break;
            case 'reserved':
                this.searchType = SearchType.RESERVED_OFFER;
                break;
            case 'search':
                this.searchType = SearchType.SEARCHED_OFFER;
                break;
        }

        // load previous search
        if (this.lastCriteria.searchType === this.searchType) {
            this.search(this.lastCriteria);
        }
    }

    search(searchCriteria: SearchCriteria) {
        this.isLoading = true;
        searchCriteria.searchType = this.searchType;
        return this.roomSearchService.fetchSearchedRooms(searchCriteria)
            .pipe(take(1))
            .subscribe(
                rooms => {
                    this.rooms = rooms;
                    this.isLoading = false;
                },
                console.log
            );
    }

    submit(searchCriteriaForm: NgForm) {
        searchCriteriaForm.form.markAllAsTouched();
        if (searchCriteriaForm.valid) {
            this.search(searchCriteriaForm.value);
        }
    }

    navigateToRoom(id: number) {
        this.router.navigate(['room/', id])
    }

    ngOnDestroy() {
        this.searchObservable.unsubscribe();
    }
}
