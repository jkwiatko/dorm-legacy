import {PictureModel} from '../../shared-module/models/picture.model';

export class RoomPreviewModel {
     id: number;
     name: string;
     availableFrom: string;
     minDuration: number;
     roomsNumber: number;
     picture: PictureModel = new PictureModel();
}
