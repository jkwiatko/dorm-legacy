import {AfterViewChecked, Component, ElementRef, ViewChild} from '@angular/core';
import {MenuController, ViewWillEnter} from '@ionic/angular';
import {MessageModel} from '../models/message.model';
import {MessageService} from '../providers/message.service';
import {switchMap} from 'rxjs/operators';
import {ChatModel} from '../models/chat.model';
import {ChatPreviewModel} from '../models/chat-preview.model';

@Component({
    selector: 'app-message-chat',
    templateUrl: './message-chat.component.html',
    styleUrls: ['./message-chat.component.scss']
})
export class MessageChatComponent implements ViewWillEnter, AfterViewChecked {

    @ViewChild('scrollMe')
    private myScrollContainer: ElementRef;

    messages: MessageModel[];
    chatMates: ChatPreviewModel[];
    chat: ChatModel;

    constructor(private menuCtrl: MenuController,
                private messageService: MessageService
    ) {
    }

    ionViewWillEnter(): void {
        this.menuCtrl.open('message-menu').then();
        this.messageService.getChatMates()
            .pipe(switchMap(chatMates => {
                this.chatMates = chatMates;
                return this.messageService.getChat(this.chatMates[0].id)
            }))
            .subscribe(chat => this.chat = chat);
    }

    ngAfterViewChecked(): void {
        this.scrollToBottom();
    }

    scrollToBottom(): void {
        try {
            this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
        } catch(err) { }
    }

    send(message: MessageModel, userId: number) {
        this.messageService.sendMessageTo(message, userId);
    }

    openMenu() {
        this.menuCtrl.open('message-menu').then();
    }
}
