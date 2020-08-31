import {Component, Input, OnInit} from '@angular/core';
import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';

@Component({
    selector: 'app-message-select',
    templateUrl: './message-select.component.html',
    styleUrls: ['./message-select.component.scss']
})
export class MessageSelectComponent implements OnInit {

    @Input()
    profile: ProfilePreviewModel;

    ngOnInit(): void {
    }
}
