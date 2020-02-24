import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageableServersComponent} from './manageable-servers.component';

describe('ManageableServersComponent', () => {
    let component: ManageableServersComponent;
    let fixture: ComponentFixture<ManageableServersComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ManageableServersComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ManageableServersComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
