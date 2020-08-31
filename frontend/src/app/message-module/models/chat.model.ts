import {MessageModel} from './message.model';

export class ChatModel {
    public id: number;
    public messages: MessageModel[] = [];
}
