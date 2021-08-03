export class MaskPhoneImpl {
    constructor() {
        this.inputPhoneNumber = $('.js-phone-mask');
    }

    apply = () => {
        const phoneMaskBehavior = (val) => {
            return val.replace('/\D/g', '').length === 11 ? '(00) 0 0000-0000' : '(00) 0000-00009'
        }

        var options = {
            onkeyPress : (val, e, field, options) => {
                field.mask(phoneMaskBehavior.apply({}, args), options);
            }
        }
//        this.inputPhoneNumber.mask(phoneMaskBehavior, options)

    }

}