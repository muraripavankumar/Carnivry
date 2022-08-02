import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProfilePicDialogComponent } from './update-profile-pic-dialog.component';

describe('UpdateProfilePicDialogComponent', () => {
  let component: UpdateProfilePicDialogComponent;
  let fixture: ComponentFixture<UpdateProfilePicDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateProfilePicDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateProfilePicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
