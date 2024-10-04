package br.ifsul.justapprove.models;

import java.io.File;

public class Material {

     private Integer id;

     private String artigo;
     private File video;

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public File getVideo() {
        return video;
    }

    public void setVideo(File video) {
        this.video = video;
    }
}
