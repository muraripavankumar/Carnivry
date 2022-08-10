import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketcompComponent } from './ticketcomp.component';

describe('TicketcompComponent', () => {
  let component: TicketcompComponent;
  let fixture: ComponentFixture<TicketcompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TicketcompComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketcompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
