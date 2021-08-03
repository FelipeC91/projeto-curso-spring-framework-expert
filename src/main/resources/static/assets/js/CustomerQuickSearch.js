export class CustomerQuickSearch {
    constructor() {
        this.quickSerchModal = $('#pesquisaRapidaClientes');
        this.urlAction = $('#pesquisaRapidaClientes').find('form').attr('action');
        this.customerName = $('#customerName');
        this.quickSerchBtn = $('.js-customer-quick-search-btn');
        this.customerTableBody = $('.js-customer-table-body');
    }

    init = () => {
        const onSearchDone = (result) => {

            console.log('result ', result);

            if (result.length === 0) {
                $('.table').removeClass('hidden')
                this.customerTableBody.append(`<div style="text-align: center">
                                                    <td colspan="2">Nenhum cliente encontrado</td>
                                               </div>`)
            }   else {
                $('.table').removeClass('hidden')
                result.forEach(e => {
                    this.customerTableBody.append(
                        `
                            <tr data-customer-code="${e.codigo}" data-customer-name="${e.nome}"class="js-customer-row">
                                <td>${e.nome}</td>
                                <td>${e.telefone}</td>
                            </tr>
                        `
                    )
                });
                const selectCustomer = new SelectCustomer(this.quickSerchModal);
                selectCustomer.init()

            }
        }
        const onQuickSearchBtnClick = (event) => {
            event.preventDefault();
            console.log("name sended >> " + this.customerName.val())
            this.customerTableBody.empty();


            if (this.customerName == null || this.customerName.val().length < 3) {
                console.log("msg de erro")
                $('.js-error-message').removeClass('hidden')

            } else {
                $('.js-error-message').addClass('hidden')

                $.ajax({
                    url: this.urlAction,
                    method: 'GET',
                    contentType: 'application/json',
                    data: {
                        name: this.customerName.val()
                    },
                    success: onSearchDone.bind(this)
                });

                const selectCustomer = new SelectCustomer();
                selectCustomer.init();
            }

        }

        console.log('test')
        this.quickSerchBtn.on('click', onQuickSearchBtnClick.bind(this))

    }
}

class SelectCustomer {
    constructor(modal) {
        this.quickSerchModal = modal
        this.customerRow = $('.js-customer-row')
    }

    init = () => {

        const onSelctedCustomer = (event) => {
            this.selectedCustomer = $(event.currentTarget);

            this.quickSerchModal.modal('hide')

            $('#nomeCliente').val(this.selectedCustomer.data('customer-name'))
            $('#codigoCliente').val(this.selectedCustomer.data('customer-code'))
        }

        this.customerRow.on('click', onSelctedCustomer.bind(this))
    }


}