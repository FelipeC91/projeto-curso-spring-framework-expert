import { MaskMoneyImpl } from "./MaskMoneyImpl.js";
import { MaskPhoneImpl } from "./MaskPhoneImpl.js";
import { MaskClientTypeImpl } from "./MaskClientTypeImpl.js";
import { MaskCepImpl } from "./MaskCepImpl.js";
import { EstadoLoader } from "./EstadoLoader.js";
import { CidadesProvider } from "./CidadesProvider.js";
import { Security } from "./Security.js";
import { MultiselectionButtomImp } from "./MultiselectionButtomImp.js";
import { CustomerQuickSearch } from "./CustomerQuickSearch.js";
import * as beerPhotoView from "./beerPhotoView.js";
import { SellAutoComplete } from "./SellAutoComplete.js";
import { SellTableItems } from "./SellTableItems.js";
import { Sell } from "./Sell.js";
import { BtnSubmit } from "./BtnSubmit.js";
//common
const maskMoney = new MaskMoneyImpl(".js-decimal-mask", ".js-integer-mask");

const maskPhone = new MaskPhoneImpl();
const maskClientType = new MaskClientTypeImpl();
const maskCep = new MaskCepImpl();

const estadoLoader = new EstadoLoader();
const cidadesProvider = new CidadesProvider(estadoLoader);

const multiselectionButtomImp = new MultiselectionButtomImp();

//sell-new
const customerQuickSearch = new CustomerQuickSearch();
const sellAutocomplete = new SellAutoComplete();
const btnSubmit = new BtnSubmit();

export const currensyFormater = (value) => {
  numeral.language("pt-br");
  return numeral(value).format("0,0.00");
};

export const cleanCurrencyFormat = (formatedValue) => {
  numeral.language("pt-br");
  return numeral().unformat(formatedValue);
};

$(window).load(() => {
  var security = new Security();

  security.enableSecurityRequest();
  multiselectionButtomImp.init();
  maskMoney.makeMasks();
  maskPhone.apply();
  maskClientType.init();
  //maskCep.apply();
  estadoLoader.init();
  cidadesProvider.init();

  console.log(window.location.href);

  customerQuickSearch.init();
  sellAutocomplete.init();
  const sellTableItems = new SellTableItems(sellAutocomplete);
  sellTableItems.init();

  const sell = new Sell(sellTableItems);
  sell.init();
  btnSubmit.init();
});

const form = $(".js-form-decimal-container");

form.on("submit", (e) => {
  e.preventDefault();
  maskMoney.destroyMasks();

  document.querySelector(".js-form-decimal-container").submit();
});

const addCsrfToken = (xhr) => {
  console.log("make header rest request");
  var headerAttributeKey = $("input[name=_csrf_header]").val();
  var token = $("input[name=_csrf]").val();
  console.log(headerAttributeKey);
  console.log(token);

  xhr.setRequestHeader(headerAttributeKey, token);
};

addCsrfToken.bind(this);

//## UI KIT Upload
let settings = {
  action: "/fotos",
  type: "json",
  filelimit: 1,
  allow: "*.(jpg|jpeg|png)",
  beforeSend: addCsrfToken,
  complete: (response) => {
    console.log(response);
    $("input[name=nomeFoto]").val(response.fotoNome);
    $("input[name=contentType]").val(response.contentType);

    $("#upload-drop").toggleClass("hidden");
    beerPhotoView.showBeerPhoto(response.fotoNome);

    $(".app-btnClosePhoto").on("click", () => {
      uploadDrop.toggleClass("hidden");
      $("input[name=nomeFoto]").val("");
      $("input[name=contentType]").val("");
      $(".app-beerPhoto").toggleClass("hidden");
    });
  },
};

const uploadSelect = $("#upload-select");
const uploadDrop = $("#upload-drop");

UIkit.uploadSelect(uploadSelect, settings);
UIkit.uploadDrop(uploadDrop, settings);
