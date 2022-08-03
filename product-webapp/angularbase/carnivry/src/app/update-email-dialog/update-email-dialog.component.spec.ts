import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmailDialogComponent } from './update-email-dialog.component';

describe('UpdateEmailDialogComponent', () => {
  let component: UpdateEmailDialogComponent;
  let fixture: ComponentFixture<UpdateEmailDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateEmailDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateEmailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
