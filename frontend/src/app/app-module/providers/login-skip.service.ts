import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {AuthService} from '../../auth-module/providers/auth.service';


@Injectable({
    providedIn: 'root'
})
export class LoginSkipService implements CanActivate {

    constructor(private auth: AuthService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.auth.isLoginIn.pipe(
            take(1),
            map(isLoginIn => {
                return isLoginIn ? this.router.createUrlTree(['/profile/edit']) : true;
            }));
    }

}
