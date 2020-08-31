export class MessageModel {
    constructor(
        public from: string,
        public text: string,
        public time: Date,
    ) { }
}
