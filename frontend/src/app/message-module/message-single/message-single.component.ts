import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-message-single',
    templateUrl: './message-single.component.html',
    styleUrls: ['./message-single.component.scss']
})
export class MessageSingleComponent implements OnInit {

    @Input()
    isUserMessage: boolean;

    @Input()
    userName: string;

    @Input()
    text: string;

    constructor() {
    }

    ngOnInit(): void {
    }

}
