export class RoomDto {
    id: number;
    name: string;
    ownerId: number;
    position: {
        longitude: number;
        latitude: number;
    };
}
