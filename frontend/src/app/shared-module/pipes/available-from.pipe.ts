import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
import {environment} from '../../../environments/environment';

@Pipe({
  name: 'availableFrom'
})
export class AvailableFromPipe implements PipeTransform {

  transform(dateString: string): string {
      if(dateString) {
          const availableFrom = moment(dateString, environment.dateFormat);
          return availableFrom.month()+1 +'.'+ availableFrom.year();
      }
      return 'dzisiaj'
  }

}
