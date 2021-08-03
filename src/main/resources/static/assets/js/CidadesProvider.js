export class CidadesProvider {
    constructor(estados) {
        this.estadoSelect = estados;
        this.cidadeSelect = $('#cidade')
        this.loadingImg = $('.js-img-loading');
    }

    init = () => {

        const onEstadoSelected = (event, codigoEstado, loadingImg) => {
            const onRequesSend = () => {
                this.loadingImg.show();
            }

            const onRequestComplete = () => {
                this.loadingImg.hide();
            }

            const onResponseIsDone = (cities) => {

                var options = [];

                options = cities.map( city => `<option value="${city.codigo}">${city.nome}</option>`) ;
                options.push(`<option value="0" selected disabled>Selecione...</option>`);
                console.log(cities)

                this.cidadeSelect.removeAttr("disabled");
                this.cidadeSelect.html(options);


            }

            if (codigoEstado) {
                const response = $.ajax({
                    url: this.cidadeSelect.data('url'),
                    method: 'GET',
                    contentType: 'application/json',
                    data: {
                        'estado': codigoEstado
                    },
                    beforeSend: onRequesSend.bind(this),
                    complete: onRequestComplete.bind(this)
                });

                response.done(onResponseIsDone.bind(this));
            }


        };

        this.estadoSelect.on('alterado', onEstadoSelected.bind(this))
    }
}