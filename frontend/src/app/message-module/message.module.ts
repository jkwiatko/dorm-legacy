import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from '../auth-module/providers/auth-guard.service';
import {MessageChatComponent} from './message-chat/message-chat.component';
import {SharedModule} from '../shared-module/shared.module';
import {IonicModule} from '@ionic/angular';
import {FormsModule} from '@angular/forms';
import { MessageMenuComponent } from './message-menu/message-menu.component';
import { MessageSelectComponent } from './message-select/message-select.component';
import { MessageSingleComponent } from './message-single/message-single.component';


const routes: Routes = [
    {path: 'message/pick', component: MessageMenuComponent, canActivate: [AuthGuardService]},
    {path: 'message/:chatId', component: MessageChatComponent, canActivate: [AuthGuardService]},
];

@NgModule({
    declarations: [MessageChatComponent, MessageMenuComponent, MessageSelectComponent, MessageSingleComponent],
    exports: [
        MessageMenuComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        SharedModule,
        IonicModule,
        FormsModule
    ]
})

export class MessageModule {
}
