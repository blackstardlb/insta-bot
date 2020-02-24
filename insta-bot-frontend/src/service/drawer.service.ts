import {Injectable} from '@angular/core';
import {MatDrawer} from '@angular/material';
import {DeviceService} from './device.service';

@Injectable({
    providedIn: 'root'
})
export class DrawerService {
    aDrawer: MatDrawer;

    constructor(private deviceService: DeviceService) {
    }

    set drawer(value: MatDrawer) {
        this.aDrawer = value;
    }

    closeDrawer() {
        if (this.aDrawer) {
            this.aDrawer.close();
        }
    }

    closeDrawerIfHandset() {
        this.deviceService.isHandsetNow().then(isHandset => {
            console.log('is handset: ' + isHandset);
            if (isHandset) {
                this.closeDrawer();
            }
        });
    }
}
