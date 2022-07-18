import { TestBed } from '@angular/core/testing';

import { UpdateEventService } from './update-event.service';

describe('UpdateEventService', () => {
  let service: UpdateEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
