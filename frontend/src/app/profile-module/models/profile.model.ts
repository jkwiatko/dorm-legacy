import {PictureModel} from '../../shared-module/models/picture.model';
import {RoomPreviewModel} from '../../shared-module/models/room-preview.model';

export class ProfileModel {
    id?: number;
    rentedRoomId?: number;
    firstName: string;
    lastName: string;
    profilePictures: PictureModel[] = [];
    birthDate: Date;
    description: string;
    gender: string;
    workingIn: string;
    studyingAt: string;
    interests: string[] = [];
    inclinations: string[] = [];
    cleaningPolicy: string;
    smokingPolicy: string;
    petPolicy: string;
    guestsPolicy: string;
    ownedRooms: RoomPreviewModel[] = [];
}
