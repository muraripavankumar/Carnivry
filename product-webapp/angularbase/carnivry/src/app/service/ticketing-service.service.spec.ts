import { TestBed } from '@angular/core/testing';

import { TicketingServiceService } from './ticketing-service.service';

describe('TicketingServiceService', () => {
  let service: TicketingServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketingServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
