import { TestBed } from '@angular/core/testing';

import { EventCategoriesService } from './event-categories.service';

describe('EventCategoriesService', () => {
  let service: EventCategoriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventCategoriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
