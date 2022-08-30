import { currensyFormater, cleanCurrencyFormat } from "./script.js";

export class Sell {
  constructor(sellTableItems) {
    this.sellTableItems = sellTableItems;
    this.valorTotalBox = $(".js-valor-total-box");
    this.currencyValorTotalBox = $(".js-aw-box__value");

    this.valorFreteInput = $("#valorFrete");
    this.valorDescontoInput = $("#valorDesconto");

    this.valorTotalState = 0;
    this.valorFreteState = 0;
    this.valorDescontoState = 0;
  }

  init() {
    const onValoresAlterados = () => {
      const total =
        this.valorTotalState + this.valorFreteState - this.valorDescontoState;
      const valorTotalFormatted = currensyFormater(total);
      this.valorTotalBox.html(valorTotalFormatted);

      if (total < 0) {
        this.currencyValorTotalBox.addClass("bw-valor-negativo");
      } else {
        this.currencyValorTotalBox.removeClass("bw-valor-negativo");
      }
    };

    const onValorFreteAlterado = (evt) => {
      this.valorFreteState = cleanCurrencyFormat($(evt.target).val());
      this.valorFreteInput.val($(evt.target).val());
    };

    const onSellTableItensUpdate = (evt, valorTotal) => {
      console.log(valorTotal);
      this.valorTotalState = valorTotal;

      const valorTotalFormatado = currensyFormater(this.valorTotalState);
      console.log("total " + valorTotalFormatado);
      this.valorTotalBox.html(currensyFormater(valorTotalFormatado));
    };

    const onValoreDescontoAlterado = (evt) => {
      this.valorDescontoState = cleanCurrencyFormat($(evt.target).val());
      this.valorDescontoInput.val($(evt.target).val());
    };

    this.sellTableItems.on(
      "sellTableItensUpdate",
      onSellTableItensUpdate.bind(this)
    );

    this.sellTableItems.on(
      "sellTableItensUpdate",
      onValoresAlterados.bind(this)
    );

    this.valorFreteInput.on("change", onValorFreteAlterado.bind(this));
    this.valorFreteInput.on("change", onValoresAlterados.bind(this));

    this.valorDescontoInput.on("change", onValoreDescontoAlterado.bind(this));
    this.valorDescontoInput.on("change", onValoresAlterados.bind(this));
  }
}
