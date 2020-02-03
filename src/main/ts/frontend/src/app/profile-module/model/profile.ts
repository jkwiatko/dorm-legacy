import {Picture} from "../../shared/model/picture.model";
import {Room} from "../../room-module/model/room";

export class Profile{
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
    rooms: Room[] = [];
}
