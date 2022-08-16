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
import { CountdownModule } from 'ngx-countdown';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UpdateEventComponent } from './update-event/update-event.component';
import { MatDialogModule } from '@angular/material/dialog';
import { EmailLinkComponent } from './email-link/email-link.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AddHeaderInterceptor } from './interceptor/add-header.interceptor';
import { ProfileComponent } from './profile/profile.component';
import { UpdateEmailDialogComponent } from './update-email-dialog/update-email-dialog.component';
import { UpdateDOBDialogComponent } from './update-dobdialog/update-dobdialog.component';
import { UpdateProfilePicDialogComponent } from './update-profile-pic-dialog/update-profile-pic-dialog.component';
import { UpdatePhoneDialogComponent } from './update-phone-dialog/update-phone-dialog.component';
import { UpdateAddressDialogComponent } from './update-address-dialog/update-address-dialog.component';
import { PostedEventsComponent } from './posted-events/posted-events.component';
import { MyGenreDialogComponent } from './my-genre-dialog/my-genre-dialog.component';
import { SeatingUIComponent } from './seating-ui/seating-ui.component';
import { ViewPageComponent } from './view-page/view-page.component';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { DatePipe } from '@angular/common';
import { CarouselModule } from './carousel/carousel.module';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { DialogBoxChooseCityComponent } from './dialog-box-choose-city/dialog-box-choose-city.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NextDirective } from './next.directive';
import { PrevDirective } from './prev.directive';
import { SearchComponent } from './search/search.component';
import { PastEventsComponent } from './past-events/past-events.component';
import { UpcomingEventsComponent } from './upcoming-events/upcoming-events.component';
import { TicketComponent } from './ticket/ticket.component';

import { WishlistComponent } from './wishlist/wishlist.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// import { FlexLayoutModule } from "@angular/flex-layout";



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
    PostedEventsComponent,
    MyGenreDialogComponent,
    ViewPageComponent,
    NavigationComponent,
    SeatingUIComponent,
    WishlistComponent,


    LandingPageComponent,
    FooterComponent,
    HeaderComponent,
    DialogBoxChooseCityComponent,
    NextDirective,
    PrevDirective,
    SearchComponent,
    PastEventsComponent,
    UpcomingEventsComponent,
    PageNotFoundComponent,
    TicketComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatStepperModule,
    ReactiveFormsModule,
    CountdownModule,
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
    MatToolbarModule,
    LayoutModule,
    MatSidenavModule,
    MatListModule,
    MatTabsModule,
    MatExpansionModule,
    MatGridListModule,
    MatToolbarModule,
    MatMenuModule,
    CarouselModule,
    FlexLayoutModule,
    MatProgressSpinnerModule
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
