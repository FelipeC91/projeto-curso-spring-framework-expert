
const beerPhotoContainer = $('.beerPhotoContainer');

export const showBeerPhoto = (photoName) => {

    const markup = `<div class="row app-beerPhoto" >
                        <div class="col-md-4"></div>
                        <div class="col-md-4 text-center">
                        
                        <button type="button" class="btn btn-link app-btnClosePhoto"><span class="glyphicon glyphicon-remove"></span></button>

                        <a href="#" class="thumbnail" style="max-width: 200px">
                            <img src="/fotos/temp/${photoName}">
                        </a>
                
                    </div>`


    console.log(photoName)
    beerPhotoContainer.append(markup);
};
