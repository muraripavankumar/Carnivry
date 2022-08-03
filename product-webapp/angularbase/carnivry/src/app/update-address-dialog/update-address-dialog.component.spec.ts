import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAddressDialogComponent } from './update-address-dialog.component';

describe('UpdateAddressDialogComponent', () => {
  let component: UpdateAddressDialogComponent;
  let fixture: ComponentFixture<UpdateAddressDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateAddressDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateAddressDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
