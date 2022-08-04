import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyGenreDialogComponent } from './my-genre-dialog.component';

describe('MyGenreDialogComponent', () => {
  let component: MyGenreDialogComponent;
  let fixture: ComponentFixture<MyGenreDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyGenreDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyGenreDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
