import { currensyFormater } from "./script.js";

export class SellAutoComplete {
  constructor() {
    this.input = $(".js-sku-name-search-input");
    this.emitter = $({});
    this.on = this.emitter.on.bind(this.emitter);
  }

  init = () => {
    const onSelectedItem = () => {
      const sellectedItem = this.input.getSelectedItemData();
      this.emitter.trigger("onSelectedItem", sellectedItem);
      this.input.val("");
      this.input.focus();
    };

    const resolveTemplate = (nome, cerveja) => {
      cerveja.foto =
        cerveja.foto.length < 1 ? "cerveja-mock.png" : cerveja.foto;

      return `  <div class="bw-tabela-item" th:fragment="autoCompleteFragment">
                                <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--foto">
                                  <img src="/fotos/thumbnail/thumbnail.${
                                    cerveja.foto
                                  }" class="img-responsive" />
                                </div>
                                <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--detalhes">
                                  <span class="bw-tabela-cerveja-nome">${nome}</span>
                                  <span class="label  label-default">${
                                    cerveja.sku
                                  }</span>
                                  <span class="bw-tabela-cerveja-origem">${
                                    cerveja.origem
                                  }</span>
                                </div>
                                <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--valores">
                                  <div class="bw-tabela-cerveja-valor-medio">R$${currensyFormater(
                                    cerveja.valor
                                  )}</div>
                                </div>
                              </div>
                            `;
    };

    var options = {
      url: (skuOrName) => "/beer?skuOrName=" + skuOrName,
      getValue: "nome",
      minCharNumber: 3,
      requestDelay: 300,

      ajaxSettings: {
        contentType: "application/json",
      },

      template: {
        type: "custom",
        method: resolveTemplate,
      },

      list: {
        onChooseEvent: onSelectedItem.bind(this),
      },
    };

    this.input.easyAutocomplete(options);
  };
}
