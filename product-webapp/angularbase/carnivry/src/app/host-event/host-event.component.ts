import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormArray, FormGroup } from '@angular/forms';
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
import { validateStartDate } from '../validations/startDateValidator';
import Validation from '../validations/timeValidation';
import { PriceCategory } from '../model/price-category';
import { Router } from '@angular/router';



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
  allLanguages: string[] = ['ASSAMESE', 'BENGALI', 'ENGLISH', 'GUJARATI', 'HINDI', 'KANNADA', 'KASHMIRI', 'KONKANI', 'MALAYALAM', 'MARATHI', 'NEPALI', 'ORIYA', 'PUNJABI', 'SANSKRIT', 'TAMIL', 'TELUGU', 'URDU'];
  genres: string[] = [];
  genreCtrl = new FormControl('');
  languageCtrl = new FormControl('');
  filteredLanguages: Observable<string[]>;
  filteredGenres: Observable<string[]>;
  allGenres: string[] = ['ADVENTURE', 'ART', 'ASTROLOGY', 'BUSINESS', 'COMEDY', 'CEREMONY', 'DANCE', 'DRAMA', 'EDUCATION', 'FOOD', 'GAMES', 'HEALTH_N_FITNESS', 'KIDS', 'LITERATURE', 'MOVIE', 'MUSIC', 'RELIGION', 'SEMINAR', 'SOCIAL', 'SPIRITUAL', 'SPORTS', 'TECHNOLOGY', 'THRILLER', 'MISCELLANEOUS'];
  // countries: string[] = ['Bangladesh', 'China', 'India', 'Nepal', 'Pakistan'];
  countries:string[] = ["Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua & Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central Arfrican Republic","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cuba","Curacao","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauro","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","North Korea","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russia","Rwanda","Saint Pierre & Miquelon","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","St Kitts & Nevis","St Lucia","St Vincent","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad & Tobago","Tunisia","Turkey","Turkmenistan","Turks & Caicos","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States of America","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Virgin Islands (US)","Yemen","Zambia","Zimbabwe"];
  filteredCountries:Observable<String[]>;
  filteredCountriesCtrl=new FormControl('');
  posterPic: any;
  thumbnailPosterPicDataUrl: string = "";
  landscapePosterPicDataUrl: string = "";
  mouseDown: boolean = false;
  selectedItems: number[] = [];
  // priceList: any[] = [];
  activePrice: number = -1;
  column: number = 0;
  row: number = 0;
  alphaRows: string[] = [];
  ticketPrice: number = null;
  // seatCategoryList: string[] = [];
  seatCategoryOptions: string[] = ['Platinum', 'Gold', 'Silver', 'Common'];
  filteredSeatCategories: Observable<string[]>;
  seatCategoryCtrl = new FormControl('');
  priceCatogoryList: PriceCategory[] = [];
  colorPalate: string[] = ['#DE3163', '#FF7F50', '#40E0D0', '#FFBF00', '#6495ED', '#CCCCFF', '#DFFF00', '#9FE2BF','#00FF7F','#DDA0DD','#FFA500','#B0E0E6','#FAFAD2'];
  colorIndexCounter: number = 0;
  isEventUpdatable: boolean = false;
  hostUserName:string=localStorage.getItem('name');
  hostEmailId:string=localStorage.getItem('email');


  hostEventForm = this.fb.group({
    eventId: [''],
    title: ['', [Validators.required, Validators.maxLength(100)]],
    eventDescription: ['', [Validators.required, Validators.minLength(5)]],
    userName: [this.hostUserName],
    userEmailId: [this.hostEmailId],
    artists: this.fb.array([]),
    genre: this.fb.array([]),
    languages: this.fb.array([]),
    posters: this.fb.array([]),
    eventTimings: this.fb.group({
      startDate: ['', [Validators.required, validateStartDate]],
      endDate: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required]
    },
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
    totalSeats: ['']
  });


  constructor(private fb: FormBuilder, private managementService: ManagementService, private snackbar: MatSnackBar, private updateEventService: UpdateEventService,private router:Router) {
    this.eventData = new Event();
  }

  ngOnInit(): void {
    this.updateEventService.obj.subscribe((data) => this.existingEventData = data);
    this.filteredGenres = this.genreCtrl.valueChanges.pipe(
      startWith(''),
      map((genre: string | '') => (genre ? this._filter(genre) : this.allGenres.slice())),
    );

    this.filteredLanguages = this.languageCtrl.valueChanges.pipe(
      startWith(''),
      map((language: string | '') => (language ? this.filterLanguages(language) : this.allLanguages.slice()))
    );

    this.filteredSeatCategories = this.seatCategoryCtrl.valueChanges.pipe(
      startWith(''),
      map(value => this.filterSeatCategory(value || '')),
    );
    this.filteredCountries=this.hostEventForm.get('venue.address.country').valueChanges.pipe(
      startWith(''),
      map(value=> this.filterCountry(value || ''))
    );
    
    this.onUpdateMode();

  }

 

  //////////////////  Initializing component for Update Event  ////////////////////
  //when component is being used to update the event,
  // then the input fields and the formGroup needs to be pre-set according to the imcomming event data.
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

      var thumbnailCtrl = new FormControl('', Validators.required);
      var landscapeCtrl = new FormControl('');
      thumbnailCtrl.setValue(this.existingEventData.posters[0]);
      landscapeCtrl.setValue(this.existingEventData.posters[1]);
      (<FormArray>this.hostEventForm.get('posters')).push(thumbnailCtrl);
      (<FormArray>this.hostEventForm.get('posters')).push(landscapeCtrl);
      this.thumbnailPosterPicDataUrl = this.existingEventData.posters[0];
      this.landscapePosterPicDataUrl = this.existingEventData.posters[1];

      // incoming date format is in ISO format, so extracting the date separately, to avoid format mismatch.
      const sDate = this.existingEventData.eventTimings.startDate.split('T')[0];
      const eDate = this.existingEventData.eventTimings.endDate.split('T')[0];
      this.hostEventForm.get('eventTimings.startDate').setValue(sDate);
      this.hostEventForm.get('eventTimings.endDate').setValue(eDate);

this.hostEventForm.get('eventTimings.startTime').setValue(this.existingEventData.eventTimings.startTime);
      this.hostEventForm.get('eventTimings.endTime').setValue(this.existingEventData.eventTimings.endTime);
      this.hostEventForm.get('venue').setValue(this.existingEventData.venue);

      this.isEventUpdatable = this.pastEventCheck(sDate, eDate, this.existingEventData.eventTimings.startTime);

      this.existingEventData.seats.forEach((s) => {
        const sCtrl = new FormGroup({});

        sCtrl.addControl('seatId', new FormControl('', Validators.required));
        sCtrl.addControl('row', new FormControl('', Validators.required));
        sCtrl.addControl('colm', new FormControl('', Validators.required));
        sCtrl.addControl('seatPrice', new FormControl('0.0001', Validators.required));
        sCtrl.addControl('status', new FormControl('NOT BOOKED'));

        sCtrl.addControl('seatCategory', new FormControl(''));

        sCtrl.get('seatId').setValue(s.seatId);
        sCtrl.get('row').setValue(s.row);
        sCtrl.get('colm').setValue(s.colm);
        sCtrl.get('seatPrice').setValue(s.seatPrice);
        sCtrl.get('status').setValue(s.status);
        sCtrl.get('seatCategory').setValue(s.seatCategory);

        (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
        if (s.colm > this.column)
          this.column = s.colm;
        if (s.row > this.row)
          this.row = s.row;
        // this.priceList.push(s.seatPrice);
        var priceCategory: PriceCategory = new PriceCategory();
        priceCategory.category = s.seatCategory;
        priceCategory.price = s.seatPrice;
        var counter = 0;
        this.priceCatogoryList.forEach((pc) => {
          if (pc.price === s.seatPrice && pc.category === s.seatCategory)
            counter = 1;
        });
        //only adding unique data
        if (counter === 0)
          this.priceCatogoryList.push(priceCategory);
        counter = 0;

      });
      // this.priceList = this.priceList.filter((item, index) => this.priceList.indexOf(item) === index);
      document.documentElement.style.setProperty("--colNum", <string><unknown>this.column);
      this.hostEventForm.get('totalSeats').setValue(this.existingEventData.totalSeats);
      if (this.existingEventData.totalSeats > 0 && this.row === 0) {
        this.seatType = 'no';
      }
      else if (this.existingEventData.totalSeats === 0 && this.row === 0 && this.existingEventData.seats.length === 1) {
        this.seatType = 'no';
      }
      else {
        this.seatType = 'yes';
        for (let pcl of this.priceCatogoryList) {
          for (let index = 0; index < this.existingEventData.totalSeats; index++) {
            if (pcl.category === this.existingEventData.seats[index].seatCategory && pcl.price === this.existingEventData.seats[index].seatPrice) {
              pcl.divColor = this.colorPalate[this.colorIndexCounter];
            }
          }
          this.colorIndexCounter++;
          if (this.colorIndexCounter >= this.colorPalate.length) {
            this.colorIndexCounter = 0;
          }
        }
      }
      // this.hostEventForm.get('totalSeats').setValue(this.existingEventData.seats.length);
      this.totalSeating = this.existingEventData.seats.length;
      this.eventData = this.existingEventData;
      this.alphabeticalRow(this.row);

    }
  }
  getColor(e: any): string {
    for (let pcl of this.priceCatogoryList) {
      if (pcl.category === e.seatCategory && pcl.price === e.seatPrice) {
        return pcl.divColor;
      }
    }
    return '#FFFFFF';
  }

  pastEventCheck(sDate: string, eDate: string, sTime: string): boolean {
    const today: Date = new Date();
    const currentHour: number = today.getHours();
    const currentMinute: any = today.getMinutes();
    const startingDate: Date = new Date(sDate);
    const endingDate: Date = new Date(eDate);
    const startTimeArray: string[] = sTime.split(':');
    if (endingDate.getTime() < today.getTime()) {
      return true;
    }
    else if (startingDate.toDateString() === today.toDateString()) {
      //if starting hour has passed, return true (i.e. not updateable)
      if (<number><unknown>startTimeArray[0] < currentHour) {
        console.log(' starting hour has passed, return true (i.e. not updateable)');
        return true;
      }
      //if starting hour has not passed, but minute has passed, return true(i.e. not updateable)
      else if ((<number><unknown>startTimeArray[0] === currentHour) && (<number><unknown>startTimeArray[1] < currentMinute))
      console.log('starting hour has not passed, but minute has passed, return true(i.e. not updateable)');  
      return true;
    }
    return false;
  }

  //////////////////////  Artist Input  ///////////////////////////////////
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


  /////////////////////  Genre Input  //////////////////////////////////
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


  //////////////////////  Languages Input  /////////////////////////////////

  readonly separatorKeysCodes3 = [ENTER, COMMA] as const;
  @ViewChild('languageInput') languageInput: ElementRef<HTMLInputElement>;

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
  selectedLanguages(event: MatAutocompleteSelectedEvent): void {
    this.languages.push(event.option.viewValue);
    this.languageInput.nativeElement.value = '';
    this.languageCtrl.setValue('');
    const lnCtrl = new FormControl(event.option.viewValue, Validators.required);
    (<FormArray>this.hostEventForm.get('languages')).push(lnCtrl);
  }

  private filterLanguages(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allLanguages.filter(g => g.toLowerCase().includes(filterValue));
  }

  ////////////////// ONLINE MODE ///////////////////////

  onOnlineMode(event: any) {
    if (event.value === 'online') {
      this.hostEventForm.get('venue.address.house').setValue('0');
      this.hostEventForm.get('venue.address.street').setValue('-NA-');
      this.hostEventForm.get('venue.address.city').setValue('-NA-');
      this.hostEventForm.get('venue.address.state').setValue('-NA-');
      this.hostEventForm.get('venue.address.pincode').setValue('000000');
      this.hostEventForm.get('venue.address.landmark').setValue('-NA-');
      this.seatType = 'no';
    }
    else {
      this.hostEventForm.get('venue.address.house').setValue('');
      this.hostEventForm.get('venue.address.street').setValue('');
      this.hostEventForm.get('venue.address.city').setValue('');
      this.hostEventForm.get('venue.address.state').setValue('');
      this.hostEventForm.get('venue.address.pincode').setValue('');
      this.hostEventForm.get('venue.address.landmark').setValue('');
      this.seatType = 'yes';
    }
  }
  /////////////////  Country Input  //////////////////////
  private filterCountry(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.countries.filter(option => option.toLowerCase().includes(filterValue));
  }

  /////////////////  Seat Input  //////////////////////
  seatType: string = 'yes';
  onSeatType(event: any) {
    this.seatType = event.value;
    this.hostEventForm.get('totalSeats').setValue('');
    (<HTMLInputElement>document.getElementById("totalRows")).value = '0';
    (<HTMLInputElement>document.getElementById("totalColm")).value = '0';
    // console.log(this.seatType);

  }

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
    // var sc:any = (<HTMLInputElement>document.getElementById('seatCategory')).value;
    this.totalSeating = (ro * co);
    this.column = co;
    this.hostEventForm.get('totalSeats').setValue(this.totalSeating);
    document.documentElement.style.setProperty("--colNum", co);
    this.alphabeticalRow(ro);
    for (let i = 0; i < this.totalSeating; i++) {
      // var j = 65;
      const sCtrl = new FormGroup({});

      sCtrl.addControl('seatId', new FormControl('', Validators.required));
      sCtrl.addControl('row', new FormControl('', Validators.required));
      sCtrl.addControl('colm', new FormControl('', Validators.required));
      sCtrl.addControl('seatPrice', new FormControl('0.0001', Validators.required));
      sCtrl.addControl('status', new FormControl('NOT BOOKED'));
      sCtrl.addControl('seatCategory', new FormControl(''));

      sCtrl.get('seatId').setValue(i + 1);
      sCtrl.get('row').setValue(ro);
      sCtrl.get('colm').setValue(co);


      (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);

    }
    // this.priceList=[];
    this.priceCatogoryList = [];
    this.eventData = this.hostEventForm.value;
  }

  alphabeticalRow(rows: number) {
    this.alphaRows = [];
    for (var i = 0; i < rows; i++) {
      var w: string = String.fromCharCode(65 + i % 26);
      if (i / 26 >= 1) {
        w = String.fromCharCode(64 + (Math.floor(i / 26))) + w;
      }
      this.alphaRows.push(w);
    }
  }

  mouseDownEvent() {
    if (this.mouseDown == false)
      this.mouseDown = true;
  }
  mouseUpEvent() {
    if (this.mouseDown == true)
      this.mouseDown = false;
  }

  // fieldsChange(values: any): void {
  //   if (values.currentTarget.checked == true) {
  //     this.selectedItems.push(values.currentTarget.value);
  //     const coloring: string = '#E1AEFC';
  //     const divId = 'd.' + values.currentTarget.id;
  //     (document.getElementById(divId) as HTMLElement).style.backgroundColor = coloring;
  //   }
  //   else {
  //     this.selectedItems.splice(this.selectedItems.indexOf(values.currentTarget.value), 1);
  //     const coloring: string = '#FFFFFF';
  //     const divId = 'd.' + values.currentTarget.id;
  //     (document.getElementById(divId) as HTMLElement).style.backgroundColor = coloring;
  //   }
  // }
  fieldChange(id: any) {
    var box = (document.getElementById(id) as HTMLInputElement);
    if (box.checked) {
      this.selectedItems.push(id);
      const coloring: string = '#E1AEFC';
      const divId = 'd.' + id;
      (document.getElementById(divId) as HTMLElement).style.backgroundColor = coloring;
    }
    else {
      this.selectedItems.splice(this.selectedItems.indexOf(id), 1);
      const coloring: string = '#FFFFFF';
      const divId = 'd.' + id;
      (document.getElementById(divId) as HTMLElement).style.backgroundColor = coloring;
    }

  }

  checkBox(values: number) {
    // console.log('id '+values.currentTarget.id);
    const id = values + "";

    const boxChecked = (document.getElementById(id) as HTMLInputElement).checked;
    const boxDisabled = (document.getElementById(id) as HTMLInputElement).disabled;

    if (this.mouseDown && !boxDisabled) {
      if (boxChecked) {
        (document.getElementById(id) as HTMLInputElement).checked = false;
        // this.fieldsChange(id);
        this.fieldChange(id);
      }
      else {
        (document.getElementById(id) as HTMLInputElement).checked = true;
        // this.fieldsChange(id);
        this.fieldChange(id);
      }
    }
  }
  checkDiv(values: any) {
    // console.log(values.currentTarget.id);
    const checkboxId = values.currentTarget.id.split('.');
    this.checkBox(checkboxId[1]);
  }

  setActivePrice() {
    this.activePrice = <number><undefined>(<HTMLInputElement>document.getElementById('activePrice')).value;
  }

  savePrice() {
    var sc: string = (<HTMLInputElement>document.getElementById('seatCategory')).value;
    // this.priceList.push(this.activePrice);
    // this.seatCategoryList.push(sc);
    var priceCategory: PriceCategory = new PriceCategory();
    priceCategory.category = sc;
    priceCategory.price = this.activePrice;
    priceCategory.divColor = this.colorPalate[this.colorIndexCounter];
    this.priceCatogoryList.push(priceCategory);
    this.selectedItems.forEach((s: number) => {
      this.eventData.seats[s].seatPrice = this.activePrice;
      this.eventData.seats[s].seatCategory = sc;
      // const divId = 'd.' + (s - 1);
      // (document.getElementById(divId) as HTMLElement).style.backgroundColor = this.colorPalate[this.colorIndexCounter];

    });

    this.colorIndexCounter++;
    if (this.colorIndexCounter >= this.colorPalate.length) {
      this.colorIndexCounter = 0;
    }
    this.selectedItems = [];
    this.eventData = this.hostEventForm.value;
  }

  removePrice(price: number) {

    for (var i = 0; i < this.priceCatogoryList.length; i++) {
      if (this.priceCatogoryList[i].price === price) {
        this.priceCatogoryList.splice(i, 1);
      }
    }

    this.eventData.seats.forEach((s) => {
      if (s.seatPrice === price) {
        s.seatPrice = 0.0001;
        s.seatCategory = '';
        var seatIndex = s.seatId - 1;
        (document.getElementById(<string><unknown>seatIndex) as HTMLInputElement).checked = false;
        const divId = 'd.' + seatIndex;
        (document.getElementById(divId) as HTMLElement).style.backgroundColor = '#FFFFFF';

      }
    });
  }
  setTicketPrice(values: any) {
    while ((<FormArray>this.hostEventForm.get('seats')).length !== 0) {
      (<FormArray>this.hostEventForm.get('seats')).removeAt(0);
    }
    // Online event (these events dont have seats)
    if (this.hostEventForm.get('venue.address.city').value === '-NA-') {
      var sCtrl = new FormGroup({});
      // var sc:string=(<HTMLInputElement>document.getElementById('seatCategory')).value;

      sCtrl.addControl('seatId', new FormControl('0', Validators.required));
      sCtrl.addControl('row', new FormControl('0', Validators.required));
      sCtrl.addControl('colm', new FormControl('0', Validators.required));
      sCtrl.addControl('seatPrice', new FormControl(values.currentTarget.value, Validators.required));
      sCtrl.addControl('status', new FormControl('NOT BOOKED'));
      sCtrl.addControl('seatCategory', new FormControl('Common'));

      (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
    }
    // Offline event without seat layout
    else {
      // console.log('Price value : ' + values.currentTarget.value);
      // console.log('Total seats value : ' + this.hostEventForm.get('totalSeats').value);
      for (let i = 0; i < this.hostEventForm.get('totalSeats').value; i++) {

        var sCtrl = new FormGroup({});

        sCtrl.addControl('seatId', new FormControl('', Validators.required));
        sCtrl.addControl('row', new FormControl('0', Validators.required));
        sCtrl.addControl('colm', new FormControl('0', Validators.required));
        sCtrl.addControl('seatPrice', new FormControl(values.currentTarget.value, Validators.required));
        sCtrl.addControl('status', new FormControl('NOT BOOKED'));
        sCtrl.addControl('seatCategory', new FormControl('Common'));

        sCtrl.get('seatId').setValue(i + 1);

        (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
      }
    }
    this.eventData = this.hostEventForm.value;
  }
  onPriceUpdate(values: any) {
    // var oldPrice = this.priceList[values];
    var oldPrice = this.priceCatogoryList[values].price;
    var uid = 'u.' + values;
    var newPrice: number = <number><unknown>(document.getElementById(uid) as HTMLInputElement).value;
    //if event organizer tries to update the price to negative value, then the seat prices will be update to 0.
    if (newPrice < 0) {
      newPrice = 0;
      (document.getElementById(uid) as HTMLInputElement).value = '0';
    }
    // console.log(oldPrice);
    // console.log(newPrice);
    // this.existingEventData.seats.forEach((s) => {
    //   if (s.seatPrice === oldPrice)
    //     s.seatPrice = newPrice;
    // });
    for (let s of (<FormArray>this.hostEventForm.get('seats')).controls) {

      if (s.get('seatPrice').value === oldPrice) {
        s.get('seatPrice').setValue(newPrice);
      }
    }
  }
  changeTotalSeats(values: any) {
    while ((<FormArray>this.hostEventForm.get('seats')).length !== 0) {
      (<FormArray>this.hostEventForm.get('seats')).removeAt(0);
    }
    for (let i = 0; i < this.hostEventForm.get('totalSeats').value; i++) {

      var sCtrl = new FormGroup({});

      sCtrl.addControl('seatId', new FormControl('', Validators.required));
      sCtrl.addControl('row', new FormControl('0', Validators.required));
      sCtrl.addControl('colm', new FormControl('0', Validators.required));
      sCtrl.addControl('seatPrice', new FormControl(values.currentTarget.value, Validators.required));
      sCtrl.addControl('status', new FormControl('NOT BOOKED'));

      sCtrl.get('seatId').setValue(i + 1);

      (<FormArray>this.hostEventForm.get('seats')).push(sCtrl);
    }

  }
  // Autocomplete for seat category
  private filterSeatCategory(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.seatCategoryOptions.filter(option => option.toLowerCase().includes(filterValue));
  }

  ///////////////////////  Poster Image Input  //////////////////////////////////////
  onThumbnailFileChange(event: any) {
    this.posterPic = event.target.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(this.posterPic);
    reader.onload = (_event) => {
      this.thumbnailPosterPicDataUrl = reader.result + '';
      var thumbnailCtrl = new FormControl('', Validators.required);
      thumbnailCtrl.setValue(this.thumbnailPosterPicDataUrl);
      (<FormArray>this.hostEventForm.get('posters')).push(thumbnailCtrl);
    }
  }
  onLandscapeFileChange(event: any) {
    this.posterPic = event.target.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(this.posterPic);
    reader.onload = (_event) => {
      this.landscapePosterPicDataUrl = reader.result + '';
      var landscapeCtrl = new FormControl('');
      landscapeCtrl.setValue(this.landscapePosterPicDataUrl);
      (<FormArray>this.hostEventForm.get('posters')).push(landscapeCtrl);
    }
  }


  ///////////////////////  Submit /Update Event  /////////////////////////////////////
  onSubmit() {
    // this.setTimeToDate();
    this.eventData = this.hostEventForm.value;
    console.log(this.eventData);
    this.managementService.postHostEvent(this.eventData).subscribe((data) => {
      if (data.status === 201) {
        this.snackbar.open('Event Uploaded Successfully!', ' ', {
          duration: 3000
        });
        this.router.navigate(['/landing-page']);
      }
      else{
        this.snackbar.open('Sorry! Event could not be uploaded. Please try again.', ' ', {
          duration: 3000
        });
        this.router.navigate(['/landing-page']);
      }
    });
  }


  onUpdate() {
    // this.setTimeToDate();
    this.eventData = this.hostEventForm.value;
    // console.log(this.eventData);
    this.managementService.updateHostEvent(this.eventData).subscribe((data) => {
      if (data.status === 200) {
        this.snackbar.open('Event Updated Successfully!', ' ', {
          duration: 3000
        });
        this.router.navigate(['/landing-page']);
      }
      else {
        this.snackbar.open('Sorry! Event could not be uploaded. Please try again.', ' ', {
          duration: 3000
        });
        this.router.navigate(['/landing-page']);
      }
    });
  }
}





////////////////////////////////////////////////////
/*NOTES: 
1. change the 'userEmailId' value to the actual emailId from the Session storage.
2. change the min character requirement for event description to 50.
*/