export class MaskClientTypeImpl {
    constructor() {
        this.customerTypeRadios = document.querySelectorAll('.js-customer-type-radio');
        this.customerClientTypelabel = $('[for=cpfOrCnpj]');
        this.customerClientTypeInput = $('#cpfOrCnpj');
    }

    init = () => {
         // for ( let i = 0; i < this.customerTypeRadios.size();i++) {
         //     const id = this.customerTypeRadios[i].id;
         //     this.customerTypeRadios[i].value =id;
         //     console.log(this.customerTypeRadios[i])
         // };

        const onTypeSelected = (event) => {
            console.log(event.target)
            applyMask.call(this, event.target);
        }

        const applyMask = (typeSelected) => {
            console.log('selected >>')

            console.log(typeSelected.id)
            if (typeSelected.id === 'JURIDICA') {
                //typeSelected.value = 'JURIDICA'
                this.customerClientTypelabel.text('CNPJ');
                this.customerClientTypeInput.val('')
                this.customerClientTypeInput.mask('00.000.000/0000-00')
                this.customerClientTypeInput.removeAttr('disabled')

            } else if (typeSelected.id === 'FISICA') {
                //typeSelected.value = 'FISICA'
                this.customerClientTypelabel.text('CPF');
                this.customerClientTypeInput.val('')
                this.customerClientTypeInput.mask('000.000.000-00')
                this.customerClientTypeInput.removeAttr('disabled')

            }

        }
        console.log(this.customerTypeRadios)
        this.customerTypeRadios.forEach(e => e.addEventListener('click', onTypeSelected.bind(this)) )
        this.customerTypeRadios.forEach(e => e.value = e.id);
        // if (typeSelected) {
        //     applyMask.call(this, typeSelected);
        // }
    }

    // onTypeSelected = (event) => {
    //     console.log('evnt -> ' + event);
    // }
}