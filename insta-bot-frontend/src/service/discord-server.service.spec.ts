import {TestBed} from '@angular/core/testing';

import {DiscordServerService} from './discord-server.service';

describe('DiscordServerService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: DiscordServerService = TestBed.get(DiscordServerService);
        expect(service).toBeTruthy();
    });
});
