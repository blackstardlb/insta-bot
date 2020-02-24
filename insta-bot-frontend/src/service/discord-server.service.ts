import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ManageableServer} from '../model/manageable-server';
import {environment} from '../environments/environment';
import {map} from 'rxjs/operators';
import {EmbeddedResource} from '../model/hateoas';
import {FullDiscordServer} from '../model/base-discord-server';

@Injectable({
    providedIn: 'root'
})
export class DiscordServerService {

    constructor(private http: HttpClient) {
    }

    getManageableServers(): Observable<Array<ManageableServer>> {
        return this.http.get<EmbeddedResource>(`${environment.serverURL}/users/me/manageable-servers`).pipe(
            // tslint:disable-next-line:no-string-literal
            map(embedded => embedded._embedded['manageableServers'] as Array<ManageableServer>)
        );
    }

    getServer(id: string): Observable<FullDiscordServer> {
        return this.http.get<FullDiscordServer>(`${environment.serverURL}/servers/${id}`);
    }

    updateServer(server: FullDiscordServer): Observable<FullDiscordServer> {
        return this.http.post<FullDiscordServer>(`${environment.serverURL}/servers/${server.id}`, server);
    }
}
