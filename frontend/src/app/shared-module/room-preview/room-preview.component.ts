import {Component, Input, OnInit} from '@angular/core';
import {RoomPreviewModel} from '../models/room-preview.model';
import {Router} from '@angular/router';

@Component({
    selector: 'app-room-preview',
    templateUrl: './room-preview.component.html',
    styleUrls: ['./room-preview.component.scss']
})
export class RoomPreviewComponent implements OnInit {

    @Input() room: RoomPreviewModel;
    @Input() navigateOnClick: boolean;
    @Input() displayPrice = true;

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    navigateToRoom() {
        if(this.navigateOnClick) {
            this.router.navigate(['/room/edit', this.room.id]).then();
        }
    }

}
