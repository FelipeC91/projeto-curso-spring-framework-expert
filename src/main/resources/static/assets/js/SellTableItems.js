import { currensyFormater } from "./script.js";

export class SellTableItems {
  constructor(autocomplete) {
    this.autocomplete = autocomplete;
    this.tableContainer = $(".js-tabela-cervejas-container");
    this.uuid = $("#uuid").val();
    this.emitter = $({});
    this.on = this.emitter.on.bind(this.emitter);
  }

  init = () => {
    const onRecivedItem = (sellItems) => {
      this.tableContainer.empty();

      if (sellItems == null) {
        this.tableContainer.append(`
          <div class="bw-tabela-cervejas__vazio">
            <i class="glyphicon glyphicon-shopping-cart"></i>
            <span>Você ainda não adicionou nenhuma cerveja.</span>
          </div>
        `);
      } else {
        let valorTotal = 0;
        sellItems.forEach((item) => {
          item.cerveja.foto =
            item.cerveja.foto !== null ? "cerveja-mock.png" : cerveja.foto;

          const html = `<div class="bw-tabela-item js-tabela-item" data-valor-total="">
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--foto">
                              <img src="/fotos/thumbnail/thumbnail.${
                                item.cerveja.foto
                              }" class="img-responsive"/>
                          </div>
  
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--detalhes">
                              <span class="bw-tabela-cerveja-nome">${
                                item.cerveja.nome
                              }</span>
                              <span class="label  label-default">${
                                item.cerveja.sku
                              }</span>
                              <span class="bw-tabela-cerveja-origem" >${
                                item.cerveja.origem
                              }</span>
                          </div>
  
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--valores">
                              <div class="bw-tabela-cerveja-valor-pequeno">
                                  <input type="number" maxlength="3" class="bw-tabela-cerveja-campo-quantidade js-tabela-qtd-item"
                                                          data-codigo-cerveja="${
                                                            item.cerveja.codigo
                                                          }" value="${
            item.quantidade
          }"/>
                                  <span>x R$ ${item.valorUnitario}</span>
                              </div>
  
                              <div class="bw-tabela-cerveja-valor-grande">R$ ${currensyFormater(
                                item.valorTotal
                              )}</div>
                          </div>
  
                          <div class="bw-tabela-item__painel-exclusao">
                              <span class="bw-tabela-item__titulo-exclusao">Deseja excluir este ítem da venda?</span>
                              <buttom class="btn btn-danger js-exclusao-item-btn" data-codigo-cerveja="${
                                item.cerveja.codigo
                              }">Excluir</buttom>
                          </div>
                      </div>                         
                          `;

          this.tableContainer.append(html);
          valorTotal += item.valorTotal;

          $(".js-tabela-qtd-item").on("change", onQtdItemChange.bind(this));
          $(".js-tabela-item").on("dblclick", onDoubleClick);
          $(".js-exclusao-item-btn").on("click", onDeleteItemClick.bind(this));

          this.emitter.trigger("sellTableItensUpdate", valorTotal);
        });
      }
    };

    const onQtdItemChange = (evt) => {
      const input = $(evt.target);
      let quantidade = input.val();

      const fieldConstraint = new RegExp(/\D/, "g");

      console.log(
        "regex test " + fieldConstraint.test(quantidade) || quantidade < 1
      );

      if (fieldConstraint.test(quantidade) || quantidade < 1) {
        console.log(fieldConstraint.test(quantidade));
        input.val(1);
        quantidade = 1;
      } else {
        console.log(quantidade);

        const cervejaCodigo = input.data("codigo-cerveja");

        const response = $.ajax({
          url: "/sell/item/" + cervejaCodigo,
          method: "PUT",
          data: {
            quantidade,

            uuid: this.uuid,
          },
        });

        response.done(onRecivedItem.bind(this));
      }
    };

    const onDoubleClick = (evt) => {
      console.log("solicitando-exclusao");
      const currentTarget = $(evt.currentTarget);
      currentTarget.toggleClass("solicitando-exclusao");
    };

    const onDeleteItemClick = (evt) => {
      const cervejaCodigo = $(evt.target).data("codigo-cerveja");
      console.log(cervejaCodigo);
      var response = $.ajax({
        url: `/sell/item/${this.uuid}/${cervejaCodigo}`,
        method: "DELETE",
      });

      response.done(onRecivedItem.bind(this));
    };

    const onItemSelecionado = (evt, item) => {
      this.tableContainer.empty();

      var response = $.ajax({
        url: "item",
        method: "POST",
        data: {
          codigo: item.codigo,
          uuid: this.uuid,
        },
      });

      response.done(onRecivedItem.bind(this));
    };

    this.autocomplete.on("onSelectedItem", onItemSelecionado.bind(this));
  };
}
