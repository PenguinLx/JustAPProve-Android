package br.ifsul.justapprove.models;

public class Usuario {

    private Integer id;

    private String email;
    private String senha;
    private int ponto;
    private String userName;

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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }
}
