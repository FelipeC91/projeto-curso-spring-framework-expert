export class BtnSubmit {
  constructor() {
    this.submitBtn = $(".js-submit-btn");
    this.mainForm = $(document.forms["mainForm"]);
  }

  init = () => {
    const onSubmit = (evt) => {
      evt.preventDefault();

      const clickedBtn = $(evt.target);

      const action = clickedBtn.data("action");
      console.log(action);

      const actionInput = $("<input>");
      actionInput.attr("name", action);
      console.log(actionInput);

      this.mainForm.append(actionInput);
      this.mainForm.submit();
    };

    this.submitBtn.on("click", onSubmit.bind(this));
  };
}
