package br.ifsul.justapprove.models;

import java.util.List;

public class Materia {

   private Integer id;

   protected String nome;
   protected String descricao;


    protected TipoMateria tipo;
    //UMA MATERIA, MUITOS MATERIAIS(ONE TO MANY)
    protected List<Material> materiais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Integer getId() {
        return id;
    }
}
