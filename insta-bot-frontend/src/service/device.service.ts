import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {map, shareReplay, take} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class DeviceService {

    constructor(private breakpointObserver: BreakpointObserver) {
    }

    public isHandset: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
        .pipe(
            map(result => result.matches),
            shareReplay()
        );

    public isHandsetNow() {
        return this.breakpointObserver.observe(Breakpoints.Handset)
            .pipe(
                map(result => result.matches),
                take(1)
            ).toPromise();
    }
}
