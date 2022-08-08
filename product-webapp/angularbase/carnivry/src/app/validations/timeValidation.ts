import { L } from "@angular/cdk/keycodes";
import { AbstractControl, ValidatorFn } from "@angular/forms";

export default class Validation {
  static match(startingDate: string, endingDate: string, startTime: string, endTime: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const startDateCtrl = controls.get(startingDate);
      const endDateCtrl = controls.get(endingDate);
      const startDate: Date = new Date(startDateCtrl.value);
      const endDate: Date = new Date(endDateCtrl.value);
      const startTimeCtrl = controls.get(startTime);
      const endTimeCtrl = controls.get(endTime);
      const startTimeArray = startTimeCtrl.value.split(':');
      const endTimeArray = endTimeCtrl.value.split(':');
      const dateFormat=(startDate+'')?.split(' ');

    //  console.log(startDate.getDate());
    

      if (startDateCtrl.value == '' || endDateCtrl.value == '' || startTimeCtrl.value == '' || endTimeCtrl.value == '') {
        return null;
      }
      //for date in MongoDB format(ISO) no validation required
      else if(dateFormat.length===1){
        return null;
      }
      else if (dateFormat.length>1 && (startDate.getTime() != endDate.getTime())) {
        return null;
      }
      //for date in UTC format
      // else if (dateFormat.length>1 && (startDate != endDate)) {
      //   console.log("Start date not equal to end date");
      //     return null;
      //   }
      else {
        //if hours same, then endTime minutes must be greater than startTime minutes, otherwise invalid 
        if (startTimeArray[0] === endTimeArray[0] && startTimeArray[1] > endTimeArray[1]) {
          // console.log('hour same');
          controls.get('endTime')?.setErrors({ matching: true });
          return {
            matching: true
          }
        }
        //if startTime hour > endTime hour then invalid
        else if (startTimeArray[0] > endTimeArray[0]) {
          // console.log('start hour>end hour');
          controls.get('endTime')?.setErrors({ matching: true });
          return {
            matching: true
          }
        }
        // console.log('other');
        return null;
      }
    }
  }
}