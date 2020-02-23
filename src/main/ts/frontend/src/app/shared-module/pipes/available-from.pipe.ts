import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment";

@Pipe({
  name: 'availableFrom'
})
export class AvailableFromPipe implements PipeTransform {

  transform(value: any, args?: any): string {
      if(value) {
          let availableFrom = moment(new Date(value));
          return availableFrom.date() +'.'+ availableFrom.year();
      }
      return 'dzisiaj'
  }

}
