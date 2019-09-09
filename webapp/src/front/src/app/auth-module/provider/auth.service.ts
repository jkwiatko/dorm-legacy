import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private client: HttpClient) { }

    register(registerFormValue: any) {
        return this.client.post(environment.api + 'auth/register', registerFormValue);
    }
}
