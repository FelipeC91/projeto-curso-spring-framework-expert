package br.com.personalportifolio.brewer.storage.local;

import br.com.personalportifolio.brewer.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component("fotoStorageLocal")
public class FotoStorageLocal implements FotoStorage {
    private static final Logger LOGER = LoggerFactory.getLogger(FotoStorageLocal.class);

    private Path baseLocalDirectory;
    private Path temporaryDirectory;

    public FotoStorageLocal() {
        this.baseLocalDirectory = FileSystems.getDefault().getPath(System.getenv("HOME"), "brewerFotos");

        makeDirectory();
    }

    private void makeDirectory() {
        try {
            Files.createDirectories(this.baseLocalDirectory);
            this.temporaryDirectory = FileSystems.getDefault().getPath(this.baseLocalDirectory.toString(), "temp");
            System.out.println(temporaryDirectory);

            Files.createDirectories(this.temporaryDirectory);

            if (LOGER.isDebugEnabled()) {
                LOGER.debug("Diretório para armazenar fotos criado");
                LOGER.debug("default directory >>" + this.baseLocalDirectory.toAbsolutePath());
                LOGER.debug("PAsta temporaryDirectoryoraria >>" + temporaryDirectory.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("erro ao criar diretorio", e);
        }

    }

    @Override
    public void salvar(String foto) {
        try {
             Files.move(this.temporaryDirectory.resolve(foto), this.baseLocalDirectory.resolve(foto));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao mover foto temporária", e);
        }

        try {
            Thumbnails.of(this.baseLocalDirectory.resolve(foto).toString())
                        .size(40, 68)
                        .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar thumb da imagem", e);
        }
    }

    @Override
    public String salvarTemporariamente(MultipartFile[] files) {
        String novoNome = null;
        if (files != null && files.length > 0) {
            var arquivo = files[0];
            System.out.println(arquivo.toString());
            novoNome = rename(arquivo.getOriginalFilename());
            try {
                arquivo.transferTo(new File(this.temporaryDirectory.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome) );

            } catch (IOException e) {
                throw new RuntimeException("erro ao salvar foto",e);
            }
        }

        return novoNome;
    }

    @Override
    public byte[] getFotoTemporaria(String nome) {
        try {
            return Files.readAllBytes(this.temporaryDirectory.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o arquivo", e);
        }
    }

    @Override
    public byte[] recuperar(String nome) {
        try {
            return Files.readAllBytes(this.baseLocalDirectory.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o thumb", e);
        }
    }


    private String rename(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }


}
