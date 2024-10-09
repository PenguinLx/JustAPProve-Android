package br.ifsul.justapprove.models;

import java.util.List;

public class Simulado {

    private Integer id;
     private List<Questao> questoes;

     private int pontos;

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public Integer getId() {
        return id;
    }
}
