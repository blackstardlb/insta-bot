import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ManageableServer} from '../../model/manageable-server';
import {DiscordServerService} from '../../service/discord-server.service';
import {DrawerService} from '../../service/drawer.service';

@Component({
    selector: 'app-manageable-servers',
    templateUrl: './manageable-servers.component.html',
    styleUrls: ['./manageable-servers.component.scss']
})
export class ManageableServersComponent implements OnInit {
    servers: Observable<Array<ManageableServer>>;

    constructor(private discordServerService: DiscordServerService, private drawerService: DrawerService) {
    }

    ngOnInit() {
        this.servers = this.discordServerService.getManageableServers();
    }

    closeDrawer() {
        this.drawerService.closeDrawerIfHandset();
    }
}
