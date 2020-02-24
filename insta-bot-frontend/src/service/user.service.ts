import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import {environment} from '../environments/environment';
import {User} from '../model/user';
import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {Token} from '../model/token';
import {catchError, map, tap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private me: Subject<User> = new BehaviorSubject<User>(null);

    constructor(private http: HttpClient, private cookieService: CookieService) {
    }

    refreshMe(): Observable<User> {
        return this.http.get<User>(environment.serverURL + '/users/me')
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    return of(null);
                }),
                tap(user => {
                    this.me.next(user);
                })
            );
    }

    getMe(): Observable<User> {
        return this.me;
    }

    isMeAvailable(): Observable<boolean> {
        return this.me.pipe(map(me => me !== null));
    }

    saveMyToken(token: Token): Observable<void> {
        this.cookieService.set(environment.discordCookieName, JSON.stringify(token));
        return this.refreshMe().pipe(map(() => void 0));
    }

    getMyToken(): Token {
        return JSON.parse(this.cookieService.get(environment.discordCookieName));
    }

    doIHaveAToken() {
        return this.cookieService.check(environment.discordCookieName);
    }

    logout() {
        this.cookieService.delete(environment.discordCookieName);
        this.me.next(null);
    }
}
