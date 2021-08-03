export class MultiselectionButtomImp {

    constructor() {
        this.multiselectionStatusBtn = $('.js-status-btn');
        this.selectablesStatus = $('.js-selectable-status')
        this.allSelectStatus = $('.js-selectable-status-all')
    }

    init = () => {
        const onChangeStatus = (event) => {
            const btnTextContent = $(event.currentTarget).data('status');
            const btnTargetUrl = $(event.currentTarget).data('url');

            const checkedStatus = this.selectablesStatus.filter(':checked')

            const codigos = $.map(checkedStatus, (c => $(c).data('codigo')))
            console.log(codigos)
            if (codigos) {
                $.ajax({
                    url: btnTargetUrl,
                    method: 'PUT',
                    data: {
                        codigos: codigos,
                        status: btnTextContent
                    },
                    success: () => {
                        window.location.reload();
                    }
                })
            }
        };

        const onAllSelected = () => {
            var toggleStatus = this.allSelectStatus.prop('checked');
                this.selectablesStatus.prop('checked', toggleStatus)
                enableButtons.call(this, toggleStatus)
        }

        const onSingleSelected = (event) => {
            const isTargetChecked = $(event.currentTarget).prop('checked');

            if (isTargetChecked == false) {
                this.allSelectStatus.prop('checked', false)
            }

            const anyChecked = $.map(this.selectablesStatus, i => $(i).prop('checked') ).filter(i => i == true);

            enableButtons.call(this, anyChecked.length)

        }

        const enableButtons = (status) => {
            status ? this.multiselectionStatusBtn.removeClass('disabled') : this.multiselectionStatusBtn.addClass('disabled')
        }


        this.multiselectionStatusBtn.on('click', onChangeStatus.bind(this));
        this.allSelectStatus.on('click', onAllSelected.bind(this))
        this.selectablesStatus.on('click', onSingleSelected.bind(this))

    }


}