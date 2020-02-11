import {Picture} from "../../shared-module/model/picture.model";
import {RoomModel} from "../../room-module/model/room.model";

export class ProfileModel{
    firstName: string;
    lastName: string;
    profilePicture: Picture = null;
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
    rooms: RoomModel[] = [];
}
