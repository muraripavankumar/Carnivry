import { AbstractControl, ValidatorFn } from "@angular/forms";

export default class ImageValidation {
    static match(picUrl: string): ValidatorFn {
        const picType = picUrl.split(";", 1)[0].substring(5);
        return (controls: AbstractControl) => {
           
            console.log(picType);
            if (picUrl === null) { return null; }
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
}