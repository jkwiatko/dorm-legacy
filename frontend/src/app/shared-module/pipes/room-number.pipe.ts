import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roomNumber'
})
export class RoomNumberPipe implements PipeTransform {

  transform(numberOfRooms: number): string {
      return numberOfRooms > 4 ? numberOfRooms + ' pokoji' : numberOfRooms > 1 ? numberOfRooms + ' pokoje' : '1 pokój';
  }

}
