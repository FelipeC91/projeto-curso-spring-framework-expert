
export  class MaskMoneyImpl {
    constructor(decimalsSelector, integersSelector) {
        this.decimals = $(decimalsSelector);
        this.integers = $(integersSelector);
    }

    makeMasks() {
        this.decimals.maskMoney({decimal: ',', thousands: '.'});
        this.integers.maskMoney({precision: 0, thousands: '.'})
    }

    destroyMasks() {
        let unmaskeds = this.decimals.maskMoney('unmasked')

        let i = 0;
         for (let decimal of this.decimals) {
             decimal.value = unmaskeds[i];
             i++
         }
    }
}