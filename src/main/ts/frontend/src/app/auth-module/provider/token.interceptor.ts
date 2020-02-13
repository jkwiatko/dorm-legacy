import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenModel} from "../model/token.model";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    constructor() {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        request = request.clone({
            setHeaders: {
                Authorization: 'Bearer ' + (new TokenModel().merge(JSON.parse(localStorage.getItem('access_token')))).token
            }
        });

        return next.handle(request);
    }
}
