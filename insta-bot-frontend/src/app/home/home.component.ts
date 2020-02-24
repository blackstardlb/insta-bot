import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {Observable} from 'rxjs';
import {User} from '../../model/user';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    me: Observable<User>;

    constructor(private userService: UserService) {
    }

    ngOnInit() {
        this.me = this.userService.getMe();
    }

}
