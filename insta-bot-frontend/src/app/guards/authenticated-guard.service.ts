import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateChild, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {FetchTokenService} from '../../service/fetch-token.service';
import {flatMap, map} from 'rxjs/operators';
import {UserService} from '../../service/user.service';

@Injectable({
    providedIn: 'root'
})
export class AuthenticatedGuard implements CanActivateChild {
    constructor(private fetchTokenService: FetchTokenService, private userService: UserService, private router: Router) {
    }

    validateTokenOrRedirect(commands: any[]) {
        return this.userService.refreshMe()
            .pipe(
                flatMap(() => this.userService.isMeAvailable()),
                map(isMeAvailable => {
                    return isMeAvailable ? true : this.router.createUrlTree(commands);
                })
            );
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (this.userService.doIHaveAToken()) {
            console.log('Already have token should validate now');
            return this.validateTokenOrRedirect(['/']);
        }

        return this.router.createUrlTree(['/']);
    }
}
