import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogBoxChooseCityComponent } from './dialog-box-choose-city.component';

describe('DialogBoxChooseCityComponent', () => {
  let component: DialogBoxChooseCityComponent;
  let fixture: ComponentFixture<DialogBoxChooseCityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogBoxChooseCityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogBoxChooseCityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
