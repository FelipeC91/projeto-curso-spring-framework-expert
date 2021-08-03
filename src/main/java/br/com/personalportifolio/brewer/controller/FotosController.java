package br.com.personalportifolio.brewer.controller;

import br.com.personalportifolio.brewer.dto.FotoDTO;
import br.com.personalportifolio.brewer.storage.FotoStorage;
import br.com.personalportifolio.brewer.storage.FotoStorageRunnable;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fotos")
public class FotosController {

    @Autowired
    @Qualifier("fotoStorageLocal")
    private FotoStorage fotoStorage;

    @PostMapping
    public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] multipartFile) {
        var result = new DeferredResult<FotoDTO>();
        System.out.println(">>>>> files[0]" + multipartFile[0].getSize());

        var thread = new Thread(new FotoStorageRunnable(multipartFile, result, fotoStorage));
        thread.start();

        return result;
    }

    @GetMapping("/temp/{nome:.*}")
    public byte[] getFotoTemporaria(@PathVariable String nome) {
        return fotoStorage.getFotoTemporaria(nome);
    }

    @GetMapping("/thumbnail/{nome:.*}")
    public byte[] recuperar(@PathVariable String nome) {
        return fotoStorage.recuperar(nome);
    }

}
