import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'gender'
})
export class GenderPipe implements PipeTransform {

  transform(value: string): string {
    if(value === 'MALE') {
        return 'Mężczyzna';
    } else if (value === 'FEMALE') {
        return 'Kobieta';
    }
    return ''
  }
}
