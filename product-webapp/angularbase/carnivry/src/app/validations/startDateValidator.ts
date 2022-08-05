import { AbstractControl } from "@angular/forms";

export function validateStartDate(control:AbstractControl){
    let presentDate:Date=new Date();
    let inputDate:Date=control.value;
   
    if(control.value===null){
        return null;
    }
    else{
        if(inputDate<presentDate){
            return {
                startDateValidator:true
            }
        }
        return null;
    }
}