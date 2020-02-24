import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {BehaviorSubject, Subject, Subscription} from 'rxjs';
import {map} from 'rxjs/operators';
import {UserService} from '../../service/user.service';
import {MatDrawer} from '@angular/material/sidenav';
import {DeviceService} from '../../service/device.service';
import {DrawerService} from '../../service/drawer.service';

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html',
    styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit, OnDestroy, AfterViewInit {

    isHandset = this.deviceService.isHandset;

    constructor(private deviceService: DeviceService, private drawerService: DrawerService, private userService: UserService) {
    }

    isAuthenticated: Subject<boolean>;
    title = 'Instabot';
    @ViewChild('drawer', {static: false}) drawer: MatDrawer;
    private subscription: Subscription;

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    ngOnInit(): void {
        this.isAuthenticated = new BehaviorSubject<boolean>(false);
        this.subscription = this.userService.getMe().pipe(map(me => me !== null)).subscribe(me => {
            this.isAuthenticated.next(me);
        });
    }

    ngAfterViewInit(): void {
        this.drawerService.drawer = this.drawer;
    }
}
