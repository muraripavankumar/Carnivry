import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatingUIComponent } from './seating-ui.component';

describe('SeatingUIComponent', () => {
  let component: SeatingUIComponent;
  let fixture: ComponentFixture<SeatingUIComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SeatingUIComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeatingUIComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
