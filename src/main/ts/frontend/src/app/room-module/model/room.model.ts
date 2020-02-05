import {Picture} from "../../shared/model/picture.model";
import {ProfileModel} from "../../profile-module/model/profile.model";
import {AddressModel} from "./address.model";
import {PositionModel} from "./position.model";

export class RoomModel {
    constructor(init?: Partial<RoomModel>) {
        Object.assign(this, init);
    }

    id: number;
    name: string;
    description: string;
    monthlyPrice: number;
    deposit: number;
    availableFrom: string;
    minDuration: number;
    room: number;
    roomsNumber: number;
    amenities: string[] = [];
    pictures: Picture[] = [];
    owner: ProfileModel = new ProfileModel();
    address: AddressModel = new AddressModel();
    position: PositionModel = new PositionModel();
}
