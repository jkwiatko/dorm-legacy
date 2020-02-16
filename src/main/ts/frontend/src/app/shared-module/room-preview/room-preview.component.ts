import {Component, Input, OnInit} from '@angular/core';
import {RoomPreviewModel} from "../../profile-module/models/room-preview.model";

@Component({
    selector: 'app-room-preview',
    templateUrl: './room-preview.component.html',
    styleUrls: ['./room-preview.component.scss']
})
export class RoomPreviewComponent implements OnInit {

    @Input() room: RoomPreviewModel;

    constructor() {
    }

    ngOnInit() {
    }

}
