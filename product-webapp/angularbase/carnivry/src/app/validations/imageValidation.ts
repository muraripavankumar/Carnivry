import { AbstractControl} from "@angular/forms";

export function posterTypeValidation(control: AbstractControl) {
    if (control.value === null) {
        return null;
    }
    else {
        let picType = control.value.split(";", 1)[0].substring(5);
        if (picType === 'image/png' || picType === 'image/jpg' || picType === 'image/jpeg' || picType==='image/gif') {
            return null;
        }
        else {
            return { posterValidation: true }
        }
    }
}