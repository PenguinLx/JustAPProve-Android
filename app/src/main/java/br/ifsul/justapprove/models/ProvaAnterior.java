package br.ifsul.justapprove.models;

import java.io.File;

public class ProvaAnterior {

     private Integer id;

     private String titulo;
     private File pdf;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public File getPdf() {
        return pdf;
    }

    public void setPdf(File pdf) {
        this.pdf = pdf;
    }
}
