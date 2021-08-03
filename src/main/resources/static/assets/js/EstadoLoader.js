export class EstadoLoader {
    constructor() {
        this.estadosSelect = $('#estado');
        this.emitter = $({});
        this.on = this.emitter.on.bind(this.emitter)
    }

    init = () => {
        const onEstadoSelected = () => {
            console.log('selected > ' + this.estadosSelect.val())

            this.emitter.trigger('alterado', this.estadosSelect.val())
        }

        this.estadosSelect.on('change', onEstadoSelected.bind(this))
    }
}