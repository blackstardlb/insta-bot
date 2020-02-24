import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {catchError, flatMap, map} from 'rxjs/operators';
import {FetchTokenService} from '../../service/fetch-token.service';
import {UserService} from '../../service/user.service';

@Injectable({
    providedIn: 'root'
})
export class LandingGuard implements CanActivate {
    constructor(private fetchTokenService: FetchTokenService, private userService: UserService, private router: Router) {
    }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (next.queryParams.code) {
            return this.fetchTokenService.getToken(next.queryParams.code).pipe(
                flatMap(token => this.userService.saveMyToken(token)),
                flatMap(() => this.userService.isMeAvailable()),
                map(isMeAvailable => {
                    if (isMeAvailable) {
                        return this.router.createUrlTree(['/home']);
                    } else {
                        return this.router.createUrlTree(['failed-token-exchange']);
                    }
                }),
                catchError(() => of(this.router.parseUrl('failed-token-exchange')))
            );
        }

        if (this.userService.doIHaveAToken()) {
            console.log('Already have token should validate now');
            return this.validateTokenOrRedirect(['/home']);
        }

        return true;
    }

    validateTokenOrRedirect(commands: any[]) {
        return this.userService.refreshMe()
            .pipe(
                flatMap(() => this.userService.isMeAvailable()),
                map(isMeAvailable => {
                    return isMeAvailable ? this.router.createUrlTree(commands) : true;
                })
            );
    }
}
