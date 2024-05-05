import { TestBed } from '@angular/core/testing';

import { UrlCountService } from './url-count.service';

describe('UrlCountService', () => {
  let service: UrlCountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlCountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
