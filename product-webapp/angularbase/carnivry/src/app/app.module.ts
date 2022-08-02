import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatStepperModule } from '@angular/material/stepper';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatSelectModule } from '@angular/material/select';

import { HostEventComponent } from './host-event/host-event.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CallbackComponent } from './callback/callback.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { AddPreferenceComponent } from './add-preference/add-preference.component';

import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UpdateEventComponent } from './update-event/update-event.component';

import {MatDialogModule} from '@angular/material/dialog';
import { EmailLinkComponent } from './email-link/email-link.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AddHeaderInterceptor } from './interceptor/add-header.interceptor';
import { ProfileComponent } from './profile/profile.component';
import { UpdateEmailDialogComponent } from './update-email-dialog/update-email-dialog.component';
import { UpdateDOBDialogComponent } from './update-dobdialog/update-dobdialog.component';
import { UpdateProfilePicDialogComponent } from './update-profile-pic-dialog/update-profile-pic-dialog.component';
import { UpdatePhoneDialogComponent } from './update-phone-dialog/update-phone-dialog.component';
import { UpdateAddressDialogComponent } from './update-address-dialog/update-address-dialog.component';
import { LandingPageComponent } from './landing-page/landing-page.component';



// Added by Garima
import {MatTabsModule} from '@angular/material/tabs';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import { NextDirective } from './next.directive';
import { PrevDirective } from './prev.directive';
import { DatePipe } from '@angular/common';
import { CarouselModule } from './carousel/carousel.module';


@NgModule({
  declarations: [
    AppComponent,
    HostEventComponent,
    CallbackComponent,
    RegistrationComponent,
    HomeComponent,
    LoginComponent,
    EmailVerificationComponent,
    AddPreferenceComponent,
    UpdateEventComponent,
    EmailLinkComponent,
    ForgotPasswordComponent,
    ProfileComponent,
    UpdateEmailDialogComponent,
    UpdateDOBDialogComponent,
    UpdateProfilePicDialogComponent,
    UpdatePhoneDialogComponent,
    UpdateAddressDialogComponent,
    LandingPageComponent,
    NextDirective,            //added by garima
    PrevDirective             //added by garima
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatStepperModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule,
    MatButtonToggleModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,

  
//added by garima
    MatTabsModule,
    MatExpansionModule,
    MatGridListModule,
    MatToolbarModule,
    MatMenuModule,
    CarouselModule
    

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AddHeaderInterceptor,
      multi: true
    },
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
