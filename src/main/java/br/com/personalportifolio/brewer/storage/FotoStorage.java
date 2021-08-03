package br.com.personalportifolio.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage  {

    public void salvar(String foto);

    public String salvarTemporariamente(MultipartFile[] files);

    public byte[] getFotoTemporaria(String nome);

    byte[] recuperar(String nome);
}
