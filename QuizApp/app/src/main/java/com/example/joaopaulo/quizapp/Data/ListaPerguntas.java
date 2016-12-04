package com.example.joaopaulo.quizapp.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joao Paulo on 02/12/2016.
 */

public class ListaPerguntas implements Serializable {

    private int quantidadePerguntas;
    private String nivel;
    private String usuario;
    private List<Perguntas> perguntas;

    public ListaPerguntas( int quantidadePerguntas, String nivel, String usuario, List<Perguntas> perguntas) {

        this.quantidadePerguntas = quantidadePerguntas;
        this.nivel = nivel;
        this.usuario = usuario;
        this.perguntas = perguntas;
    }

    public int getQuantidadePerguntas() {

        return this.quantidadePerguntas;
    }

    public String getNivel() {

        return this.nivel;
    }

    public String getUsuario() {

        return this.usuario;
    }

    public List<Perguntas> getPerguntas() {

        return this.perguntas;
    }
}
