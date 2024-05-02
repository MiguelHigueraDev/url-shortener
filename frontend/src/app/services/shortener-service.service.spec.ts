import { TestBed } from '@angular/core/testing';

import { ShortenerServiceService } from './shortener-service.service';

describe('ShortenerServiceService', () => {
  let service: ShortenerServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShortenerServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
