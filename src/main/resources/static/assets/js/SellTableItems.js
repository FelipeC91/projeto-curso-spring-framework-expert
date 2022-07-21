export class SellTableItems {
  constructor(autocomplete) {
    this.autocomplete = autocomplete;
    this.tableContainer = $(".js-tabela-cervejas-container");
    this.uuid = $("#uuid").val();
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
        sellItems.forEach((item) => {
          item.cerveja.foto =
            item.cerveja.foto !== null ? "cerveja-mock.png" : cerveja.foto;

          const html = `<div class="bw-tabela-item js-tabela-item">
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--foto">
                              <img src="/fotos/thumbnail/thumbnail.${item.cerveja.foto}" class="img-responsive"/>
                          </div>
  
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--detalhes">
                              <span class="bw-tabela-cerveja-nome">${item.cerveja.nome}</span>
                              <span class="label  label-default">${item.cerveja.sku}</span>
                              <span class="bw-tabela-cerveja-origem" >${item.cerveja.origem}</span>
                          </div>
  
                          <div class="bw-tabela-item__coluna  bw-tabela-item__coluna--valores">
                              <div class="bw-tabela-cerveja-valor-pequeno">
                                  <input type="number" maxlength="3" class="bw-tabela-cerveja-campo-quantidade js-tabela-qtd-item"
                                                          data-codigo-cerveja="${item.cerveja.codigo}" value="${item.quantidade}"/>
                                  <span>x R$ ${item.valorUnitario}</span>
                              </div>
  
                              <div class="bw-tabela-cerveja-valor-grande">R$ ${item.valorTotal}</div>
                          </div>
  
                          <div class="bw-tabela-item__painel-exclusao">
                              <span class="bw-tabela-item__titulo-exclusao">Deseja excluir este ítem da venda?</span>
                              <buttom class="btn btn-danger js-exclusao-item-btn" data-codigo-cerveja="${item.cerveja.codigo}">Excluir</buttom>
                          </div>
                      </div>                         
                          `;

          this.tableContainer.append(html);

          $(".js-tabela-qtd-item").on("change", onQtdItemChange.bind(this));
          $(".js-tabela-item").on("dblclick", onDoubleClick);
          $(".js-exclusao-item-btn").on("click", onDeleteItemClick.bind(this));
        });
      }
    };

    const onQtdItemChange = (evt) => {
      const input = $(evt.target);
      const quantidade = input.val();
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
