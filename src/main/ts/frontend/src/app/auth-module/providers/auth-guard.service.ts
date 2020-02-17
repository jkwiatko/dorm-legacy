import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {map, take} from "rxjs/operators";
import {AuthService} from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

    constructor(private auth: AuthService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.auth.isLoginIn.pipe(
            take(1),
            map(isLoginIn => {
                return isLoginIn ? isLoginIn : this.router.createUrlTree(['/home']);
            }));
    }

}
