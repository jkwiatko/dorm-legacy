export class MessageModel {
    constructor(
        private from: string,
        private text: string,
        private time: Date,
    ) { }
}
