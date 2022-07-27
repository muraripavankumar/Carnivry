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
import { UpdateEventService } from '../service/update-event.service';
import { posterTypeValidation } from '../validations/imageValidation';
import { validateStartDate } from '../validations/startDateValidator';
import Validation from '../validations/timeValidation';



@Component({
  selector: 'app-host-event',
  templateUrl: './host-event.component.html',
  styleUrls: ['./host-event.component.css']
})
export class HostEventComponent implements OnInit {
  existingEventData: Event | null;
  eventData: Event;
  presentDate: Date = new Date();
  artists: string[] = [];
  totalSeating: number = 0;
  languages: string[] = [];
  genres: string[] = [];
  genreCtrl = new FormControl('');
  filteredGenres: Observable<string[]>;
  allGenres: string[] = ['Adventure', 'Action', 'Drama', 'Party', 'Spiritual'];
  countries: string[] = ['China', 'Bangladesh', 'India', 'Pakistan'];
  posterPic: any;
  posterPicDataUrl: string = "";
  mouseDown: boolean = false;
  selectedItems: number[] = [];
  priceList: any[] = [];
  activePrice: number = -1;
  column: number = 0;
  row: number = 0;
  minDate: Date;

  constructor(private fb: FormBuilder, private managementService: ManagementService, private snackbar: MatSnackBar, private updateEventService: UpdateEventService) {
    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
      startWith(''),
      map((genre: string | '') => (genre ? this._filter(genre) : this.allGenres.slice())),
    );
    this.eventData = new Event();
  }

  ngOnInit(): void {
    // this.presentDate = new Date().toISOString().split('T')[0];
    // (document.getElementById('rdate')as HTMLFormElement).setAttribute('min', new Date().toISOString().split('T')[0]);

    this.updateEventService.obj.subscribe((data) => this.existingEventData = data);
    this.onUpdateMode();
    const todayDate = this.presentDate.getDate();
    const currentMonth = this.presentDate.getMonth();
    const currentYear = this.presentDate.getFullYear();

    this.minDate = new Date(currentYear, currentMonth, todayDate + 1);
  }

  hostEventForm = this.fb.group({
    eventId: [''],
    title: ['', [Validators.required, Validators.maxLength(100)]],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    userEmailId: ['exampleHost@g.com'],
    artists: this.fb.array([]),
    genre: this.fb.array([]),
    languages: this.fb.array([]),
<<<<<<< HEAD
    poster: ['', [Validators.required]],
=======
    poster: ['', [Validators.required, posterTypeValidation]],
>>>>>>> f2cb16fd2f182551ef8f43363d69b56354825d4e
    eventTimings: this.fb.group({
      startDate: ['', [Validators.required, validateStartDate]],
      endDate: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    },
    // { validators: [Validation.match('startDate', 'endDate')] },
      { validators: [Validation.match('startDate', 'endDate', 'startTime', 'endTime')] }
      ),
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
  //////////////////////////////////////

  onUpdateMode() {
    if (this.existingEventData.eventId != null) {
      this.hostEventForm.get('eventId').setValue(this.existingEventData.eventId);
      this.hostEventForm.get('title').setValue(this.existingEventData.title);
      this.hostEventForm.get('eventDescription').setValue(this.existingEventData.eventDescription);
      this.existingEventData.artists.forEach((value) => {
        const artistCtrl = new FormControl(value, Validators.required);
        (<FormArray>this.hostEventForm.get('artists')).push(artistCtrl);
        this.artists.push(value);
      });
      this.existingEventData.genre.forEach((value) => {
        const gnCtrl = new FormControl(value, Validators.required);
        this.genres.push(value);
        (<FormArray>this.hostEventForm.get('genre')).push(gnCtrl);
      });
      this.existingEventData.languages.forEach((value) => {
        const langCtrl = new FormControl(value, Validators.required);
        (<FormArray>this.hostEventForm.get('languages')).push(langCtrl);
        this.languages.push(value);
      });
      this.hostEventForm.get('poster').setValue(this.existingEventData.poster);
      this.posterPicDataUrl = this.existingEventData.poster;
      this.hostEventForm.get('eventTimings.startDate').setValue(this.existingEventData.eventTimings.startDate);
      this.hostEventForm.get('eventTimings.endDate').setValue(this.existingEventData.eventTimings.endDate);
      this.hostEventForm.get('eventTimings.startTime').setValue(this.existingEventData.eventTimings.startTime);
      this.hostEventForm.get('eventTimings.endTime').setValue(this.existingEventData.eventTimings.endTime);
      this.hostEventForm.get('venue').setValue(this.existingEventData.venue);
      this.existingEventData.seats.forEach((s) => {
        const sCtrl = new FormGroup({});

        sCtrl.addControl('seatId', new FormControl('', Validators.required));
        sCtrl.addControl('row', new FormControl('', Validators.required));
        sCtrl.addControl('colm', new FormControl('', Validators.required));
        sCtrl.addControl('seatPrice', new FormControl('0.0001', Validators.required));
        sCtrl.addControl('status', new FormControl('NOT BOOKED'));

        sCtrl.get('seatId').setValue(s.seatId);
        sCtrl.get('row').setValue(s.row);
        sCtrl.get('colm').setValue(s.colm);
        sCtrl.get('seatPrice').setValue(s.seatPrice);
        sCtrl.get('status').setValue(s.status);

        (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
        if (s.colm > this.column)
          this.column = s.colm;
        if (s.row > this.row)
          this.row = s.row;
        this.priceList.push(s.seatPrice);

      });
      this.priceList = this.priceList.filter((item, index) => this.priceList.indexOf(item) === index);
      document.documentElement.style.setProperty("--colNum", <string><unknown>this.column);
      this.hostEventForm.get('totalSeats').setValue(this.existingEventData.seats.length);
      this.totalSeating = this.existingEventData.seats.length;
      this.eventData = this.existingEventData;
    }
  }

  /////////////////////////////////////////////
  get seatingControls() {
    return (<FormArray>this.hostEventForm.get('seats')).controls;
  }

  calcTotalSeats() {
    this.totalSeating = 0;
    while ((<FormArray>this.hostEventForm.get('seats')).length !== 0) {
      (<FormArray>this.hostEventForm.get('seats')).removeAt(0);
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
  // setTimeToDate(){
  //   const startTimeArray:number[]=this.hostEventForm.get('eventTimings.startTime').value.split(':');
  //   const endTimeArray:number[]=this.hostEventForm.get('eventTimings.endTime').value.split(':');
  //   const inputStartDate:Date=this.hostEventForm.get('eventTimings.startDate').value;
  //   const inputEndDate:Date=this.hostEventForm.get('eventTimings.endDate').value;
  //   inputStartDate.setHours(startTimeArray[0]);
  //   inputStartDate.setMinutes(startTimeArray[1]);
  //   inputEndDate.setHours(endTimeArray[0]);
  //   inputEndDate.setHours(endTimeArray[1]);
  //   this.hostEventForm.get('eventTimings.startDate').setValue(inputStartDate);
  //   this.hostEventForm.get('eventTimings.endDate').setValue(inputEndDate);
  //   console.log('Changed date: ');
  //   console.log(this.hostEventForm.get('eventTimings.startDate').value);

  // }
  onSubmit() {
    // this.setTimeToDate();
    this.eventData = this.hostEventForm.value;
    console.log(this.eventData);
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
  onUpdate() {
    // this.setTimeToDate();
    this.eventData = this.hostEventForm.value;
    console.log(this.eventData);
    this.managementService.updateHostEvent(this.eventData).subscribe((data) => {
      if (data.status === 200) {
        this.snackbar.open('Event Updated Successfully!', ' ', {
          duration: 3000
        });
      }
      else {
        this.snackbar.open('Sorry! Event could not be uploaded. Please try again.', ' ', {
          duration: 3000
        });
      }

    });
  }
  ///////////////////////////////////////////////////////////

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
3. on update patch mapping is to be called
4. poster has to be jpeg, jpg or png 
*/