import { AbstractControl, ValidatorFn } from "@angular/forms";

// export default class Validation {
//   static match(controlName: string, checkControlName: string): ValidatorFn {
//     return (controls: AbstractControl) => {
//       const control = controls.get(controlName);
//       const checkControl = controls.get(checkControlName);
//       console.log('contrl value: '+control.value);
//       console.log('control 1 valur : '+checkControl.value);
//       if (checkControl?.errors && !checkControl.errors['matching']) {
//         return null;
//       }
//       if (control?.value !== checkControl?.value) {
//         controls.get(checkControlName)?.setErrors({ matching: true });
//         return { matching: true };
//       } else {
//         return null;
//       }
//     };
//   }
// }






export default class Validation {
  static match(startDate: string, endDate: string, startTime: string, endTime: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const startDateCtrl = controls.get(startDate);
      const endDateCtrl = controls.get(endDate);
      const startTimeCtrl = controls.get(startTime);
      const endTimeCtrl = controls.get(endTime);
      const startTimeArray = startTimeCtrl.value.split(':');
      const endTimeArray = endTimeCtrl.value.split(':');
      if (startDateCtrl.value === null || endDateCtrl.value === null || startTimeCtrl.value === null || endTimeCtrl.value === null) {
        console.log('null input');
        return null;
      }
      else if(startDateCtrl.value===endDateCtrl.value) {
        //if hours same, then endTime minutes must be greater than startTime minutes, otherwise invalid 
        if (startTimeArray[0] === endTimeArray[0] && startTimeArray[1] > endTimeArray[1]) {
          console.log('hour same, start min greater');
          return {

            endTimeValidator: true
          }
        }
        //if startTime hour > endTime hour then invalid
        else if (startTimeArray[0] > endTimeArray[0]) {
          console.log('start hour is greater');
          endTimeCtrl?.setErrors({matching:true});
          return {
            matching: true
          }
        }
        return null;
      }
      return null;
    }
  }
}