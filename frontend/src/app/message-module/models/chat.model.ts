import {MessageModel} from './message.model';

export class ChatModel {
    constructor(
        public id: number,
        public messages: MessageModel[]
    ) { }
}
