package br.ifsul.justapprove.models;

public class Alternativa {

     private Integer id;

     private boolean correta;

     private String descricao;

    public boolean isCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }
}
