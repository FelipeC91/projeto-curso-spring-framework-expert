export class MaskCepImpl {
    constructor() {
        this.cepInput = $('#cep');
    };

    apply = () =>  {
        this.cepInput.mask('00000-000');
}

}