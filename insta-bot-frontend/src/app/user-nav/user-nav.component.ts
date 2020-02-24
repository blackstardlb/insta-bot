import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {Observable} from 'rxjs';
import {User} from '../../model/user';
import {DrawerService} from '../../service/drawer.service';

@Component({
    selector: 'app-user-nav',
    templateUrl: './user-nav.component.html',
    styleUrls: ['./user-nav.component.scss']
})
export class UserNavComponent implements OnInit {
    user: Observable<User>;

    constructor(private userService: UserService, private router: Router, private drawerService: DrawerService) {
    }

    ngOnInit() {
        this.user = this.userService.getMe();
    }

    logout() {
        this.userService.logout();
        this.drawerService.closeDrawerIfHandset();
        this.router.navigate(['/']);
    }
}
