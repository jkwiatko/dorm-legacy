import {Component, EventEmitter, Output} from '@angular/core';
import {ChatPreviewModel} from '../models/chat-preview.model';
import {ViewWillEnter} from '@ionic/angular';
import {MessageService} from '../providers/message.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-message-menu',
    templateUrl: './message-menu.component.html',
    styleUrls: ['./message-menu.component.scss']
})
export class MessageMenuComponent implements ViewWillEnter {

    @Output()
    pickedId: EventEmitter<number> = new EventEmitter<number>();

    chatMates: ChatPreviewModel[] = [];

    constructor(private messageService: MessageService,
                private router: Router) {
    }

    ionViewWillEnter(): void {
        this.messageService.getChatMates().subscribe(mates => this.chatMates = mates);
    }

    pickChat(chatMateId: number) {
        this.router.navigate(['message', chatMateId]).then();
    }
}
