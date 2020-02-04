import {Picture} from "../../shared/model/picture.model";
import {Profile} from "../../profile-module/model/profile";

export class Room {
    id: number;
    name: string;
    description: string;
    monthlyPrice: number
    deposit: number;
    availableFrom: string;
    minDuration: number;
    amenities: string[] = [];
    pictures: Picture[] = [];
    address: {
        //TODO
    };
    position: {
        longitude: number;
        latitude: number;
    };
    owner: Profile;
}
