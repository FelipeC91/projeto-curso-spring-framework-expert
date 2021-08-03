package br.com.personalportifolio.brewer.storage;

import br.com.personalportifolio.brewer.dto.FotoDTO;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] multipartFile;
    private  DeferredResult<FotoDTO> result;
    private FotoStorage fotoStorage;

    public FotoStorageRunnable(MultipartFile[] multipartFile, DeferredResult<FotoDTO> result, FotoStorage fotoStorage) {
        this.result = result;
        this.multipartFile = multipartFile;
        this.fotoStorage = fotoStorage;
    }

    @Override
    public void run() {
        System.out.println(">>>>> files[0]" + multipartFile[0].getSize());

        var fotoNome = this.fotoStorage.salvarTemporariamente(multipartFile);
        System.out.println(fotoNome);
        var contentType = multipartFile[0].getContentType();
        result.setResult(new FotoDTO(contentType, fotoNome));
    }
}
