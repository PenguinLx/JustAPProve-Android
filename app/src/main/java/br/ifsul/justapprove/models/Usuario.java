package br.ifsul.justapprove.models;

public class Usuario {

    private Integer id;

    private String email;
    private String senha;
    private int pontos;
    private String apelido;
    private String image;

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
    public int getPontos() {
        return pontos;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public String getApelido() {
        return apelido;
    }
    public void setApelido(String userName) {
        this.apelido = userName;
    }

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
