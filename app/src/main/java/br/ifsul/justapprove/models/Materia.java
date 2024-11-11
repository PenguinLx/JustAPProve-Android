package br.ifsul.justapprove.models;

import java.io.Serializable;
import java.util.List;

public class Materia implements Serializable {

   private Integer id;

   private String nome;


    private TipoMateria tipo;
    //UMA MATERIA, MUITOS MATERIAIS(ONE TO MANY)
    private Material material;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoMateria getTipo() {
        return tipo;
    }

    public void setTipo(TipoMateria tipo) {
        this.tipo = tipo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMateriais(Material material) {
        this.material = material;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
