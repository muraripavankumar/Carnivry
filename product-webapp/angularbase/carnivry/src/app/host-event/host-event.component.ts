import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray, NgForm } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';

interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-host-event',
  templateUrl: './host-event.component.html',
  styleUrls: ['./host-event.component.css']
})
export class HostEventComponent implements OnInit {
  event: Event;

  constructor(private fb: FormBuilder) {
    // this.filteredGenre = this.firstFormGroup.get("genre").valueChanges.pipe(
    //   startWith(null),
    //   map((genre: string | null) => (genre ? this._filter(genre) : this.allGenres.slice())),
    // );

  }

  ngOnInit(): void {
  }

  hostEventForm = this.fb.group({
    title: ['', Validators.required],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    artist: new FormArray([]),
    genre: ['', Validators.required],
    languages: ['', Validators.required],
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
    seats: this.fb.array([this.fb.group({
      row: ['', Validators.required],
      colm: ['', Validators.required],
      seatPrice: ['', Validators.required]
    })]),

  });



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
    }
  }
  ///////////////////////////////////////////////////////

  readonly separatorKeysCodes3 = [ENTER, COMMA] as const;
  languages: string[] = [];
  addLanguage(lang: MatChipInputEvent): void {
    const value = (lang.value || '').trim();
    // Add our fruit
    if (value) {
      this.languages.push(value);
    }
    // Clear the input value
    lang.chipInput!.clear();
  }

  removeLanguage(lang: string): void {
    const index = this.languages.indexOf(lang);
    if (index >= 0) {
      this.languages.splice(index, 1);
    }
  }
  ///////////////////////////////////////////////////////
  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredGenre: Observable<string[]>;
  genres: string[] = [];
  allGenres: string[] = ['Action', 'Adventure', 'Birthday', 'Wedding', 'Comedy', 'Drama', 'RomCom'];

  @ViewChild('genreInput') genreInput: ElementRef<HTMLInputElement>;
  addGenre(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.genres.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.hostEventForm.get('genre').setValue('');
  }

  removeGenre(g: string): void {
    const index = this.genres.indexOf(g);

    if (index >= 0) {
      this.genres.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.genres.push(event.option.viewValue);
    this.genreInput.nativeElement.value = '';
    this.hostEventForm.get('genre').setValue('');
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allGenres.filter(g => g.toLowerCase().includes(filterValue));
  }
  /////////////////////////////////////////////////////////////

  // countries: Food[] = [
  //   {value: 'china', viewValue: 'China'},
  //   {value: 'india', viewValue: 'India'},
  //   {value: 'pakistan', viewValue: 'Pakistan'},
  // ];
  countries: string[] = ['China', 'Bangladesh', 'India', 'Pakistan'];
  //////////////////////////////////////////////////////////////
  onAddMoreSeats() {
    const seatControl = new FormControl('', Validators.required);
    (<FormArray>this.hostEventForm.get('seats')).push(seatControl);
  }
  get seatingControls() {
    return (<FormArray>this.hostEventForm.get('seats')).controls;
  }

  /////////////////////////////////////////////////////////////////////
  onSubmit() {
    console.log("Form submitted");
    console.log(this.event);
  }
  onNext() {
    console.log(this.event);
    console.log(this.hostEventForm.value);
  }


}

/////////////////////////////////////////////////////////////////////////

///////////////////////////////////////******************************* */
// ERRORS:
/*
1. validators for Artist, Genre and Languages not working
*/


  // firstFormGroup = this._formBuilder.group({
  //   title: ['', Validators.required],
  //   eventDescription: ['', [Validators.required, Validators.minLength(50)]],
  //   artist: ['', Validators.required],
  //   genre: ['', Validators.required],
  //   languages: ['', Validators.required]
  // });
  // secondFormGroup = this._formBuilder.group({
  //   venueName: ['', Validators.required],
  //   house: ['', Validators.required],
  //   street: ['', Validators.required],
  //   landmark: ['', Validators.required],
  //   city: ['', Validators.required],
  //   state: ['', Validators.required],
  //   country: ['', Validators.required],
  //   pincode: ['', Validators.required],
  //   startDate: ['', Validators.required],
  //   startTime: ['', Validators.required],
  //   endDate: ['', Validators.required],
  //   endTime: ['', Validators.required],
  // });
  // thirdFormGroup = this._formBuilder.group({
  //   thirdCtrl: ['', Validators.required],
  //   seats: new FormArray([])
  // });
  // fourthFormGroup = this._formBuilder.group({
  //   fourthCtrl: ['', Validators.required],
  // });
