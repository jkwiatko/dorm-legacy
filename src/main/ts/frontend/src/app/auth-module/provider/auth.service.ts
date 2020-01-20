import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {TokenDto} from '../dto/token.dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private client: HttpClient) { }

    register(registerFormValue: any) {
        return this.client.post(environment.api + 'auth/register', registerFormValue);
    }

    login(registerFormValue: any) {
        return this.client.post<TokenDto>(environment.api + 'auth/login', registerFormValue);
    }

    addAccessToken(token: string) {
        localStorage.setItem('access_token', token);
    }
}
