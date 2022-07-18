import { TestBed } from '@angular/core/testing';

import { SocialUserService } from './social-user.service';

describe('SocialUserService', () => {
  let service: SocialUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocialUserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
