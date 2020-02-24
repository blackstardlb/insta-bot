import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {Token} from '../model/token';
import {UserService} from './user.service';

@Injectable({
    providedIn: 'root'
})
export class FetchTokenService {
    private url: string = environment.serverURL + '/auth';

    constructor(private http: HttpClient, private userService: UserService) {
    }

    getToken(code: string) {
        this.userService.logout();
        return this.http.post<Token>(this.url + '/token', {code, redirectURL: environment.clientURL});
    }
}
