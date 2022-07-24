import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddPreferenceComponent } from './add-preference/add-preference.component';
import { AppComponent } from './app.component';
import { CallbackComponent } from './callback/callback.component';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { HomeComponent } from './home/home.component';
import { HostEventComponent } from './host-event/host-event.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { UpdateEventComponent } from './update-event/update-event.component';

const routes: Routes = [{
  path: 'Carnivry',
  children: [{
    path: "",
    component: AppComponent
  },
  {
    path: "callback",
    component: CallbackComponent
  },
  {
    path: "home",
    component: HomeComponent
  },
  {
    path: "register",
    component: RegistrationComponent
  },
  {
    path: "verifyEmail",
    component: EmailVerificationComponent
  },
  {
    path: "addPreference",
    component: AddPreferenceComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "host-event",
    component: HostEventComponent
  },
  {
    path: "update-event",
    component: UpdateEventComponent
  }]
},
{
  path: '',
  redirectTo: '/Carnivry/register',
  pathMatch: 'full'
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
