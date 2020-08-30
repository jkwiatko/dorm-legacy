import {ProfilePreviewModel} from '../../shared-module/models/profile-preview.model';

export class ChatPreviewModel {
    constructor(
        public id: number,
        public profilePreview: ProfilePreviewModel
    ) { }
}
