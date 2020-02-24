import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageableServerComponent} from './manageable-server.component';

describe('ManageableServerComponent', () => {
    let component: ManageableServerComponent;
    let fixture: ComponentFixture<ManageableServerComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ManageableServerComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ManageableServerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
