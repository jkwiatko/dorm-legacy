import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment";
import {environment} from "../../../environments/environment";

@Pipe({
  name: 'dateParser'
})
export class DateParserPipe implements PipeTransform {

  transform(dateString: Date | string): Date {
      return dateString ? moment(dateString, environment.dateFormat).toDate() : null;
  }
}
