import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
import {environment} from '../../../environments/environment';

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {

  transform(dateString: Date): string {
      if(dateString) {
          const today = moment();
          const birthDate = moment(dateString.toString(), environment.dateFormat);
          return today.diff(birthDate, 'years').toString();
      }
      return '?';
  }
}
