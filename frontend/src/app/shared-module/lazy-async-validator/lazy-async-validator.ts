import {BehaviorSubject, Observable, of} from 'rxjs';
import {debounceTime, filter, map, mergeMap, take, tap} from 'rxjs/operators';
import {AbstractControl} from '@angular/forms';

export interface validatorService {
    checkIfNotValid(term): Observable<boolean>
}

export function LazyAsyncValidatorFactory(validatorService: validatorService) {
    const termDebouncer = new BehaviorSubject('');
    const validationEmitter = new BehaviorSubject(null);
    let prevTerm = '';
    let prevValidity = null;

    termDebouncer.pipe(
        map(val => (val + '').trim()),
        filter(val => val.length > 0),
        debounceTime(1000),
        mergeMap(term => {
            const obs = term === prevTerm ? of(prevValidity) : validatorService.checkIfNotValid(term);
            prevTerm = term;
            return obs;
        }),
        map(response => response ? {invalid: true} : null
        ),
        tap(validity => prevValidity = validity)
    ).subscribe(validity => {
        validationEmitter.next(validity);
    });


    return (control: AbstractControl) => {
        termDebouncer.next(control.value);
        return validationEmitter.asObservable().pipe(take(2));
    }
}
