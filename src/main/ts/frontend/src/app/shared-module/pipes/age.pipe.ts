import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {

  transform(value: string): number {
      let today = moment();
      let birthDate = moment(new Date(value));
      return today.diff(birthDate, 'years');
  }
}
