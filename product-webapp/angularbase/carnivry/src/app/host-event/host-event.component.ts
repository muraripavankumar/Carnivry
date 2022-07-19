import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray, NgForm } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';

import { ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ManagementService } from '../service/management.service';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-host-event',
  templateUrl: './host-event.component.html',
  styleUrls: ['./host-event.component.css']
})
export class HostEventComponent implements OnInit {
   eventData: Event;

  constructor(private fb: FormBuilder, private managementService: ManagementService, private snackbar: MatSnackBar) {
    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
      startWith(''),
      map((genre: string | '') => (genre ? this._filter(genre) : this.allGenres.slice())),
    );
  }

  ngOnInit(): void {
    this.setDate();
    // (document.getElementById('endDatePicker')as HTMLFormElement).setAttribute('min',this.hostEventForm.get('eventTimings').get('startDate').value);
    this.addSeatings();//initially adding a set of controls
  }

  hostEventForm = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    userEmailId: ['exampleHost@g.com'],
    artists: this.fb.array([]),
    genre: this.fb.array([]),
    languages: this.fb.array([]),
    poster:['',Validators.required],
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
  totalSeating: number = 0;
  calcTotalSeats() {
    this.totalSeating = 0;
    (<FormArray>this.hostEventForm.get('seats')).controls.forEach(element => {
      var r: number = element.get('row').value;
      var c: number = element.get('colm').value;
      this.totalSeating = this.totalSeating + (r * c);
    });
  
    this.hostEventForm.controls['totalSeats'].setValue(this.totalSeating);
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


  /////////////////////////////////////////////////////////////////////

  @ViewChild('startDateInput') startDateInput:ElementRef<HTMLInputElement>;
 presentDate:any;
 eventStartDate:any;
  setDate(){
//  (document.getElementById('startDateInput')as HTMLFormElement).setAttribute('min', new Date().toISOString().split('T')[0]);
   this.presentDate= new Date().toISOString().split('T')[0];
  }
  setStartDate(){
    console.log(this.startDateInput.nativeElement.value);
    
  }
  dateControls(){
    return this.hostEventForm.get('eventTimings');
  }

  startDateChanged() {
    this.ngOnInit();
    this.setStartDate();
    this.eventStartDate=this.startDateInput.nativeElement.value;
    // console.log(this.hostEventForm.get('eventTimings').get('startDate').value);
    // (document.getElementById('endDatePicker') as HTMLFormElement).setAttribute('min', this.hostEventForm.get('eventTimings').get('startDate').value);
  }
  //////////////////////////////////////////////////////////////////////
  posterPic: any;
  posterPicUrl: any;
  posterPicDataUrl:string;
  onFileChange(event: any) {
    this.posterPic = event.target.files[0];
  
    var reader = new FileReader();
    reader.readAsDataURL(this.posterPic);
  
    reader.onload = (_event) => {
      this.posterPicDataUrl = reader.result+'';
      console.log('PosterPicUrl :- '+this.posterPicDataUrl);//this is working
      this.hostEventForm.controls['poster'].setValue(this.posterPicDataUrl);
    }
  }


  /////////////////////////////////////////////////////////////
  // onSubmit() {
  //   const formData = new FormData();
  //   const article = this.hostEventForm.value;
  //   formData.append('event', JSON.stringify(article));
  //   formData.append('image', this.posterPic);
  //   console.log(JSON.stringify(this.posterPic));
  //   this.managementService.postHostEvent(formData).subscribe((data) => {
  //     if (data.status === 201) {
  //       this.snackbar.open('Event Uploaded Successfully!', ' ', {
  //         duration: 3000
  //       });
  //     }
  //     else
  //       alert('sorry');
  //   });
  // }

  onSubmit(){
    this.eventData=this.hostEventForm.value;
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
}


////////////////////////////////////////////////////
/*NOTES: 
1. change the 'userEmailId' value to the actual emailId from the Session storage.
2. put the validation for date and time
3. have range of dates and accept the start and end time for each date
*/