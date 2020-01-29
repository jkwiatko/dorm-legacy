import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from "rxjs";
import {TokenDto} from "../model/token.dto";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private _isLoginIn = new BehaviorSubject(false);

    get isLoginIn(): Observable<boolean> {
        return this._isLoginIn.asObservable();
    }

    constructor(private client: HttpClient) {
        if (localStorage.getItem('access_token')) {
            this._isLoginIn.next(true);
        }
    }

    register(registerFormValue: any) {
        return this.client.post(environment.api + 'auth/register', registerFormValue);
    }

    login(registerFormValue: any): Observable<TokenDto> {
        return this.client.post<TokenDto>(environment.api + 'auth/login', registerFormValue);
    }

    logout() {
        localStorage.removeItem('access_token');
        this._isLoginIn.next(false);
    }

    addAccessToken(token: string) {
        localStorage.setItem('access_token', token);
        this._isLoginIn.next(true);
    }
}
