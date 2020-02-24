import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';
import {environment} from '../environments/environment';
import {Token} from '../model/token';

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {
    constructor(private cookieService: CookieService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.cookieService.check(environment.discordCookieName)) {
            const token: Token = JSON.parse(this.cookieService.get(environment.discordCookieName));
            req = req.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + token.accessToken
                }
            });
        }
        return next.handle(req);
    }
}
