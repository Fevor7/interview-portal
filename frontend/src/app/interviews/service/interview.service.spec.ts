import { TestBed, inject } from '@angular/core/testing';

import { InterviewService } from './interview.service';

describe('CandidateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InterviewService]
    });
  });

  it('should be created', inject([InterviewService], (service: InterviewService) => {
    expect(service).toBeTruthy();
  }));
});