
import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';

@Directive({
  selector: '[appPosterImageValidation]',
  providers: [{ provide: NG_VALIDATORS, useExisting: PosterImageValidationDirective, multi: true }]
})
export class PosterImageValidationDirective implements Validator {

  constructor() { }
  validate(control: AbstractControl): ValidationErrors | null {
    if (control.value === null) {
      return {
        appPosterImageValidation: false
      }
    }
    const picUrl = control.value;
    const picType = picUrl.split(";", 1).substring(5);
    if (picType === 'image/png' || picType === 'image/jpg' || picType === 'image/jpeg') {
      console.log(picType);
      return null;
    }
    else {
      return {
        appPosterImageValidation: false
      }
    }
  }
}
  


