import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray, NgForm } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';



@Component({
  selector: 'app-host-event',
  templateUrl: './host-event.component.html',
  styleUrls: ['./host-event.component.css']
})
export class HostEventComponent implements OnInit {
  event: Event;

  constructor(private fb: FormBuilder) {
    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
      startWith(''),
      map((genre: string | '') => (genre ? this._filter(genre) : this.allGenres.slice())),
    );
  }

  ngOnInit(): void {
    // (document.getElementById('endDatePicker')as HTMLFormElement).setAttribute('max', new Date().toISOString().split('T')[0]);
    // (document.getElementById('endDatePicker')as HTMLFormElement).setAttribute('min',this.hostEventForm.get('eventTimings').get('startDate').value);
    this.addSeatings();//initially adding a set of controls
  }

  hostEventForm = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    artist: this.fb.array([]),
    genre: this.fb.array([]),
    languages: this.fb.array([]),
    eventTimings: this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    }),
    poster: [''],
    fileName: [''],
    fileType: [''],
    venue: this.fb.group({
      venueName: ['', Validators.required],
      address: this.fb.group({
        house: ['', Validators.required],
        street: ['', Validators.required],
        landmark: ['', Validators.required],
        city: ['', Validators.required],
        state: ['', Validators.required],
        country: ['', Validators.required],
        pincode: ['', Validators.required]
      }),
    }),
    seats: this.fb.array([]),
  });
  /////////////////////////////////////////////////////////
  addSeatings() {
    const seatCtrl = this.fb.group({
      row: ['', Validators.required],
      colm: ['', Validators.required],
      seatPrice: ['', Validators.required]
    });
    (<FormArray>this.hostEventForm.get('seats')).push(seatCtrl);
  }
  get seatingControls() {
    return (<FormArray>this.hostEventForm.get('seats')).controls;
  }
  removeSeating(index: number) {
    if (index >= 0) {
      (<FormArray>this.hostEventForm.get('seats')).removeAt(index);
    }
  }
  /////////////////////////////////////////////////////////
  addOnBlur = true;
  readonly separatorKeysCodes1 = [ENTER, COMMA] as const;
  artists: string[] = [];
  addArtist(artist: MatChipInputEvent): void {
    const value = (artist.value || '').trim();
    // Add new artist
    if (value) {
      const artistCtrl = new FormControl(value, Validators.required);
      (<FormArray>this.hostEventForm.get('artist')).push(artistCtrl);
      this.artists.push(value);
    }
    // Clear the input value
    artist.chipInput!.clear();
  }
  removeArtist(artist: string): void {
    const index = this.artists.indexOf(artist);
    if (index >= 0) {
      this.artists.splice(index, 1);
      (<FormArray>this.hostEventForm.get('artist')).removeAt(index);
    }
  }
  ///////////////////////////////////////////////////////

  readonly separatorKeysCodes3 = [ENTER, COMMA] as const;
  languages: string[] = [];
  addLanguage(lang: MatChipInputEvent): void {
    const value = (lang.value || '').trim();
    // Add new language
    if (value) {
      const langCtrl = new FormControl(value, Validators.required);
      (<FormArray>this.hostEventForm.get('languages')).push(langCtrl);
      this.languages.push(value);
    }
    // Clear the input value
    lang.chipInput!.clear();
  }
  removeLanguage(lang: string): void {
    const index = this.languages.indexOf(lang);
    if (index >= 0) {
      (<FormArray>this.hostEventForm.get('languages')).removeAt(index);
      this.languages.splice(index, 1);
    }
  }

  ///////////////////////////////////////////////////////
  separatorKeysCodes: number[] = [ENTER, COMMA];
  genreCtrl = new FormControl('');
  filteredGenres: Observable<string[]>;
  genres: string[] = [];
  allGenres: string[] = ['Adventure', 'Action', 'Drama', 'Party', 'Spiritual'];

  @ViewChild('genreInput') genreInput: ElementRef<HTMLInputElement>;

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our genre
    if (value) {
      const gnCtrl = new FormControl(value, Validators.required);
      this.genres.push(value);
      (<FormArray>this.hostEventForm.get('genre')).push(gnCtrl);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.genreCtrl.setValue('');
  }

  remove(gr: string): void {
    const index = this.genres.indexOf(gr);

    if (index >= 0) {
      this.genres.splice(index, 1);
      (<FormArray>this.hostEventForm.get('genre')).removeAt(index);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.genres.push(event.option.viewValue);
    this.genreInput.nativeElement.value = '';
    this.genreCtrl.setValue('');
    const gnCtrl = new FormControl(event.option.viewValue, Validators.required);
    (<FormArray>this.hostEventForm.get('genre')).push(gnCtrl);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allGenres.filter(g => g.toLowerCase().includes(filterValue));
  }
  /////////////////////////////////////////////////////////////
  countries: string[] = ['China', 'Bangladesh', 'India', 'Pakistan'];


  /////////////////////////////////////////////////////////////////////

  // @ViewChild('startDateInput') startDateInput:ElementRef<HTMLInputElement>;

  startDateChanged() {
    this.ngOnInit();
    console.log(this.hostEventForm.get('eventTimings').get('startDate').value);
    (document.getElementById('endDatePicker') as HTMLFormElement).setAttribute('min', this.hostEventForm.get('eventTimings').get('startDate').value);
  }
  //////////////////////////////////////////////////////////////////////
  onSubmit() {
    console.log("Form submitted");
    this.event = this.hostEventForm.value;
    console.log(this.event);
  }
  onNext() {
    this.event = this.hostEventForm.value;
    console.log(this.event);
    console.log(this.hostEventForm.value);
  }


}