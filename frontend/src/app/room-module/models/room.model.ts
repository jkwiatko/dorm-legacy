import {PictureModel} from '../../shared-module/models/picture.model';
import {ProfileModel} from '../../profile-module/models/profile.model';
import {AddressModel} from './address.model';
import {PositionModel} from './position.model';

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
    pictures: PictureModel[] = [];
    owner: ProfileModel = new ProfileModel();
    address: AddressModel = new AddressModel();
    position: PositionModel = new PositionModel();

    merge(merge: Partial<RoomModel>): RoomModel {
        return Object.assign(this, merge);
    }

    deepCopy(): RoomModel {
        return new RoomModel().merge(JSON.parse(JSON.stringify(this)));
    }
}
