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

    get name() {
        if(this.userName === '') {
            return this.userName;
        }
        if(this.userName === 'You') {
            return 'Ty:'
        }
        return this.userName+':'
    }

    constructor() {
    }

    ngOnInit(): void {
    }

}
