import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

import { TravellogService } from './travellog.service';

describe('TravellogService', () => {
  let service: TravellogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule]
    });
    service = TestBed.inject(TravellogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
