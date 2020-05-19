import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';
import {environment} from "../../../environments/environment";

@Pipe({
  name: 'age'
})
export class AgePipe implements PipeTransform {

  transform(dateString: Date): string {
      if(dateString) {
          let today = moment();
          let birthDate = moment(dateString.toString(), environment.dateFormat);
          return today.diff(birthDate, 'years').toString();
      }
      return "?";
  }
}
