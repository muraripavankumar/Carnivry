import { TestBed } from '@angular/core/testing';

import { ViewPageService } from './view-page.service';

describe('ViewPageService', () => {
  let service: ViewPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
