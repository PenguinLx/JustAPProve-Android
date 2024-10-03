package br.ifsul.justapprove.models;

import java.io.File;

public class Questao {
    private Integer id;

    // talvez seja byte[]
    private File descricao;

    public File getDescricao() {
        return descricao;
    }

    public void setDescricao(File descricao) {
        this.descricao = descricao;
    }
}
