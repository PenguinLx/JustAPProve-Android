package br.ifsul.justapprove.models;

import java.io.File;

public class ProvaAnterior {

     private Integer id;

     private String titulo;
     private String pdf;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
