export class RoomDto {
    id: number;
    name: string;
    ownerId: number;
    price: number;
    position: {
        longitude: number;
        latitude: number;
    };
}