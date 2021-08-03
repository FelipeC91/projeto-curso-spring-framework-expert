export class Security {
    constructor() {
        this.headerKeyToken = $('input[name=_csrf_header]').val();
        this.token = $('input[name=_csrf]').val();
    }

    enableSecurityRequest = () => {
        $(document).ajaxSend( (event, jqxhr, settings) => {
           jqxhr.setRequestHeader(this.headerKeyToken, this.token)
        }).bind(this);
    }
}