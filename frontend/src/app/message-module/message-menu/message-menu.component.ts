import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';

@Component({
    selector: 'app-message-menu',
    templateUrl: './message-menu.component.html',
    styleUrls: ['./message-menu.component.scss']
})
export class MessageMenuComponent implements OnInit {

    @Output()
    pickedId: EventEmitter<number>

    @Input()
    chatMates: ProfilePreviewModel[];

    constructor() {
    }

    ngOnInit(): void {
    }

    pickChat(chatMateId: number) {
        this.pickedId.emit(chatMateId);
    }
}
