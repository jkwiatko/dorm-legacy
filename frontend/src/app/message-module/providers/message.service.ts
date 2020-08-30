import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {MessageModel} from '../models/message.model';
import {ChatModel} from '../models/chat.model';
import {ChatPreviewModel} from '../models/chat-preview.model';

@Injectable({
    providedIn: 'root'
})
export class MessageService {

    constructor(private http: HttpClient) {
    }

    getChatMates(): Observable<ChatPreviewModel[]> {
        return this.http.get<ChatPreviewModel[]>(environment.api + 'messages/chats');
    }

    checkIfUserHasChat(userId: number): Observable<boolean> {
        return this.http.get<boolean>(environment.api + 'messages/chats/check/' + userId);
    }

    getChat(chaId: number): Observable<ChatModel> {
        return this.http.get<ChatModel>(environment.api + 'messages/chat/' + chaId)
    }

    addUserToChat(userId: number): Observable<number> {
        return this.http.patch<number>(environment.api + 'messages/chats/add', userId);
    }

    getLatestMessages(chatId: number, lastMessageDate: Date): Observable<MessageModel[]> {
        return this.http.post<MessageModel[]>(environment.api + 'messages/latest', {chatId, lastMessageDate});
    }

    sendMessageTo(message: MessageModel, userId: number): Observable<MessageModel>{
        return this.http.post<MessageModel>(environment.api + 'message/send/' + userId, message);
    }
}
