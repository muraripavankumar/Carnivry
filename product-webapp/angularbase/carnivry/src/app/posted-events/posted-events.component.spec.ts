import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostedEventsComponent } from './posted-events.component';

describe('PostedEventsComponent', () => {
  let component: PostedEventsComponent;
  let fixture: ComponentFixture<PostedEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostedEventsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostedEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
