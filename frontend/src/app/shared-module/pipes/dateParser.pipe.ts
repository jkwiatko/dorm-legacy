import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
import {environment} from '../../../environments/environment';

@Pipe({
  name: 'dateParser'
})
export class DateParserPipe implements PipeTransform {

  transform(dateString: Date | string): Date | string {
      if(environment.mobile) {
          return dateString ? moment(dateString, environment.dateFormat).format('YYYY-MM-DD') : null;
      }
      return dateString ? moment(dateString, environment.dateFormat).toDate() : null;
  }
}
