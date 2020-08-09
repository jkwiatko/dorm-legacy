import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {ActivatedRoute, Router} from '@angular/router';
import {debounceTime, take} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {RoomSearchService} from '../providers/room-search.service';
import {SearchType} from '../../shared-module/models/searchType.model';
import {SearchCriteriaModel} from '../models/search.criteria.model';

@Component({
    selector: 'app-rooms',
    templateUrl: './room-search.component.html',
    styleUrls: ['./room-search.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RoomSearchComponent implements OnInit {

    constructor(
        private roomSearchService: RoomSearchService,
        private roomService: RoomService,
        private router: Router,
        private route: ActivatedRoute
    ) {
    }

    rooms: RoomPreviewModel[] = []
    availableCities: string[] = [];
    isLoading = false;
    startDate = new Date();
    searchType: SearchType;
    searchTypeEnum = SearchType;
    title: string;

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
        this.route.url.subscribe(this.setSearchType.bind(this));
        this.roomService.fetchAvailableCities()
            .subscribe((cities => this.availableCities = cities));
        this.searchForm.valueChanges.pipe(debounceTime(500))
            .subscribe(() => this.submit(this.searchForm))
    }

    setSearchType(url) {
        switch (url[url.length - 1].path) {
            case 'own':
                this.searchType = SearchType.OWN_OFFER;
                this.title = 'Moje Oferty'
                break;
            case 'reserved':
                this.searchType = SearchType.RESERVED_OFFER;
                this.title = 'Zarazerwowane Oferty'
                break;
            case 'search':
                this.searchType = SearchType.SEARCHED_OFFER;
                this.title = 'Szukaj Ofert'
                break;
        }
    }

    search(searchCriteria: SearchCriteriaModel) {
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

    reset() {
        this.searchForm.resetForm({cityName: this.searchForm.form.get('cityName').value});
    }

    navigateToRoom(id: number) {
        this.router.navigate(['room/', id]).then()
    }

    navigateToRoommates(id: number) {
        this.router.navigate(['room/', id, 'search', 'roommate']).then();
    }
}
