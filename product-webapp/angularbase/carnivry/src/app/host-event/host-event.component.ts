import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

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
  event:Event;

  constructor(private _formBuilder: FormBuilder) {
    this.filteredGenre = this.firstFormGroup.get("genre").valueChanges.pipe(
      startWith(null),
      map((genre: string | null) => (genre ? this._filter(genre) : this.allGenres.slice())),
    );

  }

  ngOnInit(): void {
  }


  firstFormGroup = this._formBuilder.group({
    title: new FormControl('', Validators.required),
    eventDescription: ['',[ Validators.required, Validators.minLength(50)]],
    artist: [''],
    genre: new FormControl('', Validators.required),
    languages: new FormControl('', Validators.required)
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
    venueName: new FormControl('',Validators.required),
    house: new FormControl('',Validators.required),
    street: new FormControl('',Validators.required),
    landmark: new FormControl('',Validators.required),
    city: new FormControl('',Validators.required),
    state: new FormControl('',Validators.required),
    pincode: new FormControl('',Validators.required),
  });
  thirdFormGroup = this._formBuilder.group({
    thirdCtrl: ['', Validators.required],
    seats: new FormArray([])
  });
  fourthFormGroup = this._formBuilder.group({
    fourthCtrl: ['', Validators.required],
  });

 
  /////////////////////////////////////////////////////////
  addOnBlur = true;
  readonly separatorKeysCodes1 = [ENTER, COMMA] as const;
  artists: string[] = ['Artist1'];
  addArtist(artist: MatChipInputEvent): void {
    const value = (artist.value || '').trim();
    // Add our fruit
    if (value) {
      // this.ffruits.push({ name: value });
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
  languages: string[] = ['Language1'];
  addLanguage(lang: MatChipInputEvent): void {
    const value = (lang.value || '').trim();
    // Add our fruit
    if (value) {
      // this.ffruits.push({ name: value });
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
  genres: string[] = ['Drama'];
  allGenres: string[] = ['Action', 'Adventure', 'Birthday', 'Wedding', 'Comedy', 'Drama','RomCom'];

  @ViewChild('genreInput') genreInput: ElementRef<HTMLInputElement>;
  addGenre(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.genres.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.firstFormGroup.get('genre').setValue(null);
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
    this.firstFormGroup.get('genre').setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.allGenres.filter(g => g.toLowerCase().includes(filterValue));
  }
  /////////////////////////////////////////////////////////////

  foods: Food[] = [
    {value: 'china', viewValue: 'China'},
    {value: 'india', viewValue: 'India'},
    {value: 'pakistan', viewValue: 'Pakistan'},
  ];
  //////////////////////////////////////////////////////////////
  onAddMoreSeats(){
    const seatControl=new FormControl('',Validators.required);
    (<FormArray>this.thirdFormGroup.get('seats')).push(seatControl);
  }
  get seatingControls(){
    return (<FormArray>this.thirdFormGroup.get('seats')).controls;
  }
}
///////////////////////////////////////******************************* */
// ERRORS:
/* 
1. validators for Artist, Genre and Languages not working
*/