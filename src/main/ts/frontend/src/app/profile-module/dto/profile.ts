import {SafeUrl} from "@angular/platform-browser";
import {Room} from "../../room-module/dto/room";

export class Profile {
    firstName: string;
    lastName: string;
    profilePicture: ProfilePicture = null;
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

export interface ProfilePicture {
    base64String: string | SafeUrl;
    name: string;
}
