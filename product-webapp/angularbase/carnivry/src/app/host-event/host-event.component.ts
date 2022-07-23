import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray, NgForm, FormGroup } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ManagementService } from '../service/management.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Event } from "../model/event";

@Component({
  selector: 'app-host-event',
  templateUrl: './host-event.component.html',
  styleUrls: ['./host-event.component.css']
})
export class HostEventComponent implements OnInit {
  eventData: Event;
  presentDate: any;

  constructor(private fb: FormBuilder, private managementService: ManagementService, private snackbar: MatSnackBar) {
    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
      startWith(''),
      map((genre: string | '') => (genre ? this._filter(genre) : this.allGenres.slice())),
    );
    this.eventData = new Event();
  }

  ngOnInit(): void {
    this.presentDate = new Date().toISOString().split('T')[0];
    // this.addSeatings();//initially adding a set of controls
  }

  hostEventForm = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    userEmailId: ['exampleHost@g.com'],
    artists: this.fb.array([]),
    genre: this.fb.array([]),
    languages: this.fb.array([]),
    poster: ['', Validators.required],
    eventTimings: this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    }),
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
    totalSeats: ['', Validators.required]
  });

  /////////////////////////////////////////////
  get seatingControls() {
    return (<FormArray>this.hostEventForm.get('seats')).controls;
  }
  totalSeating: number = 0;
  calcTotalSeats() {
    this.totalSeating = 0;
    while ((<FormArray>this.hostEventForm.get('seats')).length !== 0) {
      (<FormArray>this.hostEventForm.get('seats')).removeAt(0)
    }

    var ro: any = (<HTMLInputElement>document.getElementById("totalRows")).value;
    var co: any = (<HTMLInputElement>document.getElementById("totalColm")).value;
    this.totalSeating = (ro * co);
    document.documentElement.style.setProperty("--colNum", co);
    for (let i = 0; i < this.totalSeating; i++) {
      const sCtrl = new FormGroup({});

      sCtrl.addControl('seatId', new FormControl('', Validators.required));
      sCtrl.addControl('row', new FormControl('', Validators.required));
      sCtrl.addControl('colm', new FormControl('', Validators.required));
      sCtrl.addControl('seatPrice', new FormControl('0.0001', Validators.required));
      sCtrl.addControl('status', new FormControl('NOT BOOKED'));

      sCtrl.get('seatId').setValue(i + 1);
      sCtrl.get('row').setValue(ro);
      sCtrl.get('colm').setValue(co);

      (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
    }
    this.eventData = this.hostEventForm.value;
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
      (<FormArray>this.hostEventForm.get('artists')).push(artistCtrl);
      this.artists.push(value);
    }
    // Clear the input value
    artist.chipInput!.clear();
  }
  removeArtist(artist: string): void {
    const index = this.artists.indexOf(artist);
    if (index >= 0) {
      this.artists.splice(index, 1);
      (<FormArray>this.hostEventForm.get('artists')).removeAt(index);
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

  //////////////////////////////////////////////////////////////////////
  posterPic: any;
  posterPicDataUrl: string;
  onFileChange(event: any) {
    this.posterPic = event.target.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(this.posterPic);
    reader.onload = (_event) => {
      this.posterPicDataUrl = reader.result + '';
      this.hostEventForm.controls['poster'].setValue(this.posterPicDataUrl);
    }
  }


  /////////////////////////////////////////////////////////////
  onSubmit() {
    this.eventData = this.hostEventForm.value;
    this.managementService.postHostEvent(this.eventData).subscribe((data) => {
      if (data.status === 201) {
        this.snackbar.open('Event Uploaded Successfully!', ' ', {
          duration: 3000
        });
      }
      else
        this.snackbar.open('Sorry! Event could not be uploaded. Please try again.', ' ', {
          duration: 3000
        });
    });
  }
  ///////////////////////////////////////////////////////////
  mouseDown: boolean = false;
  selectedItems: number[] = [];
  priceList: any[] = [];
  mouseDownEvent() {
    if (this.mouseDown == false)
      this.mouseDown = true;
  }
  mouseUpEvent() {
    if (this.mouseDown == true)
      this.mouseDown = false;
  }

  fieldsChange(values: any): void {
    if (values.currentTarget.checked == true) {
      this.selectedItems.push(values.currentTarget.value);
    }
    else {
      this.selectedItems.splice(this.selectedItems.indexOf(values.currentTarget.value), 1);
    }
  }
  checkBox(values: any) {
    let box = (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked;
    if (this.mouseDown) {
      if (box) {
        (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked = false;
        this.fieldsChange(values);
      }
      else {
        (document.getElementById(values.currentTarget.id) as HTMLInputElement).checked = true;
        this.fieldsChange(values);
      }
    }
  }
  activePrice: number = -1;
  setActivePrice() {
    this.activePrice = <number><undefined>(<HTMLInputElement>document.getElementById('activePrice')).value;
  }
  savePrice() {

    this.priceList.push(this.activePrice);
    this.selectedItems.forEach((s: number) => {
      this.eventData.seats[s - 1].seatPrice = this.activePrice;
    });
    this.selectedItems = [];
    this.eventData = this.hostEventForm.value;
    // console.log('Venue data: ');
    // console.log(this.eventData);
  }
  removePrice(price: number) {
    this.priceList.splice(this.priceList.indexOf(price), 1);
    this.eventData.seats.forEach((s) => {
      if (s.seatPrice === price) {
        s.seatPrice = 0.0001;
        var seatIndex = s.seatId - 1;
        (document.getElementById(<string><unknown>seatIndex) as HTMLInputElement).checked = false;
      }
    });
  }
}



////////////////////////////////////////////////////
/*NOTES: 
1. change the 'userEmailId' value to the actual emailId from the Session storage.
2. put the validation for date and time
3. have range of dates and accept the start and end time for each date
*/