import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from "rxjs";
import {TokenModel} from "../model/token.model";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private _isLoginIn = new BehaviorSubject(false);
    private tokenExpirationTimer: number;

    get isLoginIn(): Observable<boolean> {
        return this._isLoginIn.asObservable();
    }

    constructor(private client: HttpClient) {
        if (localStorage.getItem('access_token')) {
            const token: TokenModel = new TokenModel().merge(JSON.parse(localStorage.getItem('access_token')));
            if (token.hasNotExpired()) {
                this._isLoginIn.next(true);
                this.addAutoLogout(token.expirationDuration());
            } else {
                this.logout();
            }
        }
    }

    register(registerFormValue: any) {
        return this.client.post(environment.api + 'auth/register', registerFormValue);
    }

    login(registerFormValue: any): Observable<TokenModel> {
        return this.client.post<TokenModel>(environment.api + 'auth/login', registerFormValue);
    }

    logout() {
        localStorage.removeItem('access_token');
        this._isLoginIn.next(false);
        if (this.tokenExpirationTimer) {
            clearTimeout(this.tokenExpirationTimer);
        }
    }

    addAccessToken(token: TokenModel) {
        localStorage.setItem('access_token', JSON.stringify(token));
        this.addAutoLogout(token.expirationDuration());
        this._isLoginIn.next(true);
    }

    addAutoLogout(expirationDuration : number) {
        this.tokenExpirationTimer = setTimeout(() => {
            this.logout();
        }, expirationDuration);
    }
}
