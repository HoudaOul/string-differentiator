import { TestBed, inject } from '@angular/core/testing';

import { StringsDiffService } from './strings-diff.service';

describe('StringsDiffService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StringsDiffService]
    });
  });

  it('should ...', inject([StringsDiffService], (service: StringsDiffService) => {
    expect(service).toBeTruthy();
  }));
});
