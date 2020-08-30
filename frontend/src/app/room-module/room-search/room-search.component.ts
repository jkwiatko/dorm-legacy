import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {RoomService} from '../providers/room.service';
import {ActivatedRoute, Router} from '@angular/router';
import {debounceTime} from 'rxjs/operators';
import * as moment from 'moment';
import {NgForm} from '@angular/forms';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';
import {environment} from '../../../environments/environment';
import {SearchType} from '../../shared-module/models/searchType.model';
import {RoomSearchCriteriaModel} from '../models/room-search-criteria.model';
import {ViewWillEnter, ViewWillLeave} from '@ionic/angular';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-rooms',
    templateUrl: './room-search.component.html',
    styleUrls: ['./room-search.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RoomSearchComponent implements ViewWillEnter, ViewWillLeave {

    constructor(
        private roomService: RoomService,
        private router: Router,
        private route: ActivatedRoute
    ) {
    }

    @ViewChild('searchForm', {static: false})
    searchForm: NgForm;

    rooms: RoomPreviewModel[] = []
    availableCities: string[] = [];
    isLoading = false;
    startDate = new Date();
    searchType: SearchType;
    searchTypeEnum = SearchType;
    title: string;
    mobile = environment.mobile;

    private formSub: Subscription;
    private routeSub: Subscription;
    private shouldPreloadData = true;

    ionViewWillEnter(): void {
        this.routeSub = this.route.url.subscribe(this.setSearchType.bind(this));
        this.roomService.fetchAvailableCities()
            .subscribe((cities => this.availableCities = cities));
        this.formSub = this.searchForm.valueChanges
            .pipe(debounceTime(1000))
            .subscribe(() => this.submit(this.searchForm))
    }

    ionViewWillLeave(): void {
        this.routeSub.unsubscribe();
        this.formSub.unsubscribe();
    }

    submit(searchCriteriaForm: NgForm) {
        searchCriteriaForm.form.markAllAsTouched();
        if (searchCriteriaForm.valid) {
            this.search(searchCriteriaForm.value);
        }
    }

    reset() {
        if (this.searchType === SearchType.SEARCHED_OFFER) {
            this.searchForm.resetForm({cityName: this.searchForm.form.get('cityName').value});
        } else {
            this.searchForm.resetForm();
        }
    }

    minDate() {
        return moment(new Date()).format('YYYY-MM-DD');
    }

    maxDate() {
        return moment(new Date()).add(5, 'years').format('YYYY-MM-DD');
    }

    navigate(roomId: number) {
        const selectedRoom = this.rooms.find(room => room.id === roomId)
        switch (this.searchType) {
            case SearchType.RENTED_OFFER:
                this.navigateToRentee(roomId, selectedRoom.renteeId);
                break;
            case SearchType.OWN_OFFER:
                this.navigateToProfileSearch(roomId);
                break;
            case SearchType.RESERVED_OFFER:
            case SearchType.SEARCHED_OFFER:
            default:
                this.navigateToRoom(roomId);
        }
    }

    private search(searchCriteria: RoomSearchCriteriaModel) {
        this.isLoading = true;
        searchCriteria.searchType = this.searchType;
        return this.roomService.fetchSearchedRooms(searchCriteria)
            .subscribe(
                rooms => {
                    this.rooms = rooms;
                    this.isLoading = false;
                },
                console.log
            );
    }

    private setSearchType(url) {
        switch (url[url.length - 1].path) {
            case 'own':
                this.searchType = SearchType.OWN_OFFER;
                this.title = 'Moje Oferty'
                break;
            case 'reserved':
                this.searchType = SearchType.RESERVED_OFFER;
                this.title = 'Rezerwacje'
                break;
            case 'search':
                this.searchType = SearchType.SEARCHED_OFFER;
                this.title = 'Szukaj Ofert'
                this.shouldPreloadData = false;
                break;
            case 'rented':
                this.searchType = SearchType.RENTED_OFFER;
                this.title = 'Wynajęte'
        }
        if (this.shouldPreloadData) {
            this.submit(this.searchForm)
        }
    }

    private navigateToRentee(roomId: number, userId: number) {
        this.router.navigate(['room', roomId, 'search', 'rentee', userId]).then();
    }

    private navigateToProfileSearch(roomId: number) {
        this.router.navigate(['room', roomId, 'search', 'rentee']).then();
    }

    private navigateToRoom(roomId: number) {
        this.router.navigate(['/room', roomId]).then();
    }
}
