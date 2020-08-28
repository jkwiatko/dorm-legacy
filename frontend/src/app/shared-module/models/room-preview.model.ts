import {PictureModel} from './picture.model';

export class RoomPreviewModel {
    id: number;
    name: string;
    availableFrom: string;
    minDuration: number;
    roomsNumber: number;
    monthlyPrice: number;
    renteeId: number;
    picture: PictureModel = new PictureModel();
}
