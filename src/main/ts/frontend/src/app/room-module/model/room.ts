import {Picture} from "../../shared/model/picture.model";

export class Room {
    id: number;
    name: string;
    ownerId: number;
    price: number;
    pictures: Picture[];
    position: {
        longitude: number;
        latitude: number;
    };
}
