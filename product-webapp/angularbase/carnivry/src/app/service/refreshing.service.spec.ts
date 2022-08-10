import { TestBed } from '@angular/core/testing';

import { RefreshingService } from './refreshing.service';

describe('RefreshingService', () => {
  let service: RefreshingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RefreshingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
