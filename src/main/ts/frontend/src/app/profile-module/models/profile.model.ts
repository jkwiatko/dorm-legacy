import {PictureModel} from "../../shared-module/models/picture.model";
import {RoomPreviewModel} from "./room-preview.model";

export class ProfileModel{
    firstName: string;
    lastName: string;
    profilePictures: PictureModel[] = [];
    birthDate: string;
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
