import {Picture} from "../../shared/model/picture.model";
import {ProfileModel} from "../../profile-module/model/profile.model";
import {AddressModel} from "./address.model";
import {PositionModel} from "./position.model";

export class RoomModel {
    id: number;
    name: string;
    description: string;
    monthlyPrice: number;
    deposit: number;
    availableFrom: string;
    minDuration: number;
    houseArea: number;
    roomsNumber: number;
    amenities: string[] = [];
    pictures: Picture[] = [];
    owner: ProfileModel = new ProfileModel();
    address: AddressModel = new AddressModel();
    position: PositionModel = new PositionModel();

    merge(merge: Partial<RoomModel>) : RoomModel {
       return Object.assign(this, merge);
    }
}
