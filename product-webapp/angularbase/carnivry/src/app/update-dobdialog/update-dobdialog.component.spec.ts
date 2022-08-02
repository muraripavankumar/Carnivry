import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateDOBDialogComponent } from './update-dobdialog.component';

describe('UpdateDOBDialogComponent', () => {
  let component: UpdateDOBDialogComponent;
  let fixture: ComponentFixture<UpdateDOBDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateDOBDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateDOBDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
