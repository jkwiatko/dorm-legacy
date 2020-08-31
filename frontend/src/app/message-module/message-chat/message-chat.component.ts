import {AfterViewChecked, Component, ElementRef, ViewChild} from '@angular/core';
import {MenuController, ViewWillEnter} from '@ionic/angular';
import {MessageService} from '../providers/message.service';
import {ChatModel} from '../models/chat.model';
import {ActivatedRoute} from '@angular/router';
import {NgForm} from '@angular/forms';

@Component({
    selector: 'app-message-chat',
    templateUrl: './message-chat.component.html',
    styleUrls: ['./message-chat.component.scss']
})
export class MessageChatComponent implements ViewWillEnter, AfterViewChecked {

    chat: ChatModel = new ChatModel();

    private chatId: number;
    private chatRefreshTimer: number;
    private prevSize = 0;
    @ViewChild('scrollFrame', {static: false}) private scrollFrame: ElementRef;

    constructor(private menuCtrl: MenuController,
                private messageService: MessageService,
                private route: ActivatedRoute
    ) {
    }

    ionViewWillEnter(): void {
        this.menuCtrl.open('message-menu').then();
        this.route.params.subscribe(params => {
            this.chatId = params.chatId;
            this.addAutoRefresh(100);
        });
    }

    ngAfterViewChecked(): void {
        if(this.chat.messages.length > this.prevSize) {
            this.scrollToBottom();
            this.prevSize = this.chat.messages.length;
        }
    }


    handlePressingEnter(f: NgForm, event: KeyboardEvent) {
        if (event && event.key === 'Enter') {
            this.submit(f);
        }
    }

    submit(f: NgForm) {
        const message = f.form.value.message;
        if (message) {
            this.messageService.sendMessageTo(message, this.chat.id).subscribe()
        }
        f.reset();
    }

    addAutoRefresh(refreshRate: number) {
        this.chatRefreshTimer = setTimeout(() => {
                this.refresh();
                this.addAutoRefresh(refreshRate);
            },
            refreshRate);
    }

    skipNameIfTheSame(index: number) {
        const name = this.chat.messages[index].from;
        if(index === 0) {
            return this.chat.messages[index].from;
        }
        if(name === this.chat.messages[index - 1].from) {
            return '';
        }
        return name;
    }

    private refresh() {
        this.messageService.getChat(this.chatId).subscribe(chat => this.chat = chat);
    }

    private scrollToBottom(): void {
        this.scrollFrame.nativeElement.scroll({
            top: this.scrollFrame.nativeElement.scrollHeight,
            left: 0,
            behavior: 'smooth'
        });
    }
}
