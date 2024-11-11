package br.ifsul.justapprove.models;

import java.io.File;
import java.io.Serializable;

public class Material implements Serializable {

     private Integer id;

     private String descricao;
     private String videoEmbedd;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVideoEmbedd() {
        return videoEmbedd;
    }

    public void setVideoEmbedd(String videoEmbedd) {
        this.videoEmbedd = videoEmbedd;
    }
}
