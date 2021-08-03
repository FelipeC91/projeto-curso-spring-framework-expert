$( () => {
    form.on('submit', (event) => {
        event.preventDefault();
    });
    console.log(actionUrl)

    modal.on('shown.bs.modal', onShowModal);
    modal.on('hide.bs.modal', onHideModal);
    saveBtn.on('click', onStyleSave)

});

const onShowModal = () => {
    modalInput.focus()
};

const onHideModal = () => {
    modalInput.val('')
    modalErrorMessage.addClass('hidden')
    form.find('.form-group').removeClass('has-error');

}

const onStyleSave = () => {
    console.log('ajax');
    $.ajax({
        url : actionUrl,
        method : 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            nome: modalInput.val()
        }),
        error: onRequestError,
        success: onResponseOk
    })
}

const modal = $('#modalCadastroRapidoEstilo');
const saveBtn = modal.find('.js-save-style-btn');
const modalInput = $('#nomeEstilo')
const form = modal.find('form');
const actionUrl = form.attr('action');
const modalErrorMessage = $('.js-modal-error-message');

const onRequestError = (response) => {
    const errorMessage = response.responseText;
    console.log((errorMessage))
    modalErrorMessage.removeClass('hidden')
    modalErrorMessage.find('.message-content').append(errorMessage);
    form.find('.form-group').addClass('has-error');
}

const onResponseOk = (estilo) => {
    const estiloSelect = $('#estilo');
    estiloSelect.append(`<option value="${estilo.codigo}">${estilo.nome}</option>`);
    estiloSelect.val(estilo.codigo)

    modal.modal('hide');
}
