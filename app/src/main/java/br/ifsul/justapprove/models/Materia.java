package br.ifsul.justapprove.models;

import java.util.List;

public class Materia {

   private Integer id;

   private String nome;


    private TipoMateria tipo;
    //UMA MATERIA, MUITOS MATERIAIS(ONE TO MANY)
    private List<Material> materiais;

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

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }
}
