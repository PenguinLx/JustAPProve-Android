package br.ifsul.justapprove.models;

public class Usuario {

    private Integer id;

    private String email;
    private String senha;
    private int ponto;
    private String nome;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getPonto() {
        return ponto;
    }
    public void setPonto(int ponto) {
        this.ponto = ponto;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
