import { TestBed } from '@angular/core/testing';

import { TravellogService } from './travellog.service';

describe('TravellogService', () => {
  let service: TravellogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TravellogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
