import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePhoneDialogComponent } from './update-phone-dialog.component';

describe('UpdatePhoneDialogComponent', () => {
  let component: UpdatePhoneDialogComponent;
  let fixture: ComponentFixture<UpdatePhoneDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatePhoneDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatePhoneDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
