import {TestBed} from '@angular/core/testing';

import {FetchTokenService} from './fetch-token.service';

describe('FetchTokenService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: FetchTokenService = TestBed.get(FetchTokenService);
        expect(service).toBeTruthy();
    });
});
