import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HostEventComponent } from './host-event.component';

describe('HostEventComponent', () => {
  let component: HostEventComponent;
  let fixture: ComponentFixture<HostEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HostEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HostEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
