import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPreferenceComponent } from './add-preference.component';

describe('AddPreferenceComponent', () => {
  let component: AddPreferenceComponent;
  let fixture: ComponentFixture<AddPreferenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPreferenceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPreferenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
