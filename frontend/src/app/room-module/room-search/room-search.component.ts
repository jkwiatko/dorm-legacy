import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {take} from 'rxjs/operators';
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
    lookingForUserOffer = false;

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
        this.route.url.subscribe(this.searchForUserOffers.bind(this))
        this.roomService.fetchAvailableCities().subscribe((cities => this.availableCities = cities))
        if (this.lastCriteria.cityName) {
            this.search(this.lastCriteria);
        }
    }

    searchForUserOffers(url) {
        if (url[url.length - 1].path === 'my-offers') {
            this.lookingForUserOffer = true;
            this.search({} as SearchCriteria);
        }
    }

    search(searchCriteria: SearchCriteria) {
        this.isLoading = true;
        searchCriteria.lookingForUserOffer = this.lookingForUserOffer;
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

    submit(searchCriteriaForm : NgForm) {
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
