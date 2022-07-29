import { AbstractControl, ValidatorFn } from "@angular/forms";

export default class Validation {
  static match(startingDate: string, endingDate: string, startTime: string, endTime: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const startDateCtrl = controls.get(startingDate);
      const endDateCtrl = controls.get(endingDate);
      const startDate: Date = startDateCtrl.value;
      const endDate: Date = endDateCtrl.value;
      const startTimeCtrl = controls.get(startTime);
      const endTimeCtrl = controls.get(endTime);
      const startTimeArray = startTimeCtrl.value.split(':');
      const endTimeArray = endTimeCtrl.value.split(':');

      if (startDateCtrl.value == '' || endDateCtrl.value == '' || startTimeCtrl.value == '' || endTimeCtrl.value == '') {
        return null;
      }
      else if (startDate.getTime() != endDate.getTime()) {
        return null;
      }
      else {
        //if hours same, then endTime minutes must be greater than startTime minutes, otherwise invalid 
        if (startTimeArray[0] === endTimeArray[0] && startTimeArray[1] > endTimeArray[1]) {
          controls.get('endTime')?.setErrors({ matching: true });
          return {
            matching: true
          }
        }
        //if startTime hour > endTime hour then invalid
        else if (startTimeArray[0] > endTimeArray[0]) {
          controls.get('endTime')?.setErrors({ matching: true });
          return {
            matching: true
          }
        }
        return null;
      }
    }
  }
}