import { AbstractControl } from "@angular/forms";

export function validateEndTime(startDateCtrl:AbstractControl,endDateCtrl:AbstractControl, startTimeCtrl:AbstractControl, endTimeCtrl:AbstractControl){
    if(startDateCtrl.value===null || endDateCtrl.value===null || startTimeCtrl.value===null || endTimeCtrl.value=== null){
        return null;
    }
    else{
        const startDate:Date=startDateCtrl.value;
        const endDate:Date=endDateCtrl.value;
        //spliting the string 0->Hour, 1->Minute
        const startTimeArray=startTimeCtrl.value.split(':');
        const endTimeArray=endTimeCtrl.value.split(':');
        if(endDate>startDate){
            return null;
        }
        else{
            //if hours same, then endTime minutes must be greater than startTime minutes, otherwise invalid 
            if(startTimeArray[0]===endTimeArray[0] && startTimeArray[1]>endTimeArray[1]){
                return{
                    endTimeValidator:true
                }
            }
            //if startTime hour > endTime hour then invalid
            else if(startTimeArray[0]>endTimeArray[0]){
                return{
                    endTimeValidator:true
                }
            }
            return null;
        }
    }
}