package com.example.joaopaulo.quizapp.Data;

import java.io.Serializable;

/**
 * Created by Joao Paulo on 30/11/2016.
 */

public class Perguntas implements Serializable{

    long id;
    int imagem;
    String pergunta;
    String respostas[] = new String[4];
    String respostaCerta;

    public Perguntas(int imagem, String pergunta, String[] respostas, String respostaCerta, long id) {

        this.imagem = imagem;
        this.pergunta = pergunta;
        this.respostas = respostas;
        this.respostaCerta = respostaCerta;
        this.id = id;
    }

    public Perguntas(int imagem, String pergunta, String[] respostas, String respostaCerta) {

        this.imagem = imagem;
        this.pergunta = pergunta;
        this.respostas = respostas;
        this.respostaCerta = respostaCerta;
    }

    public void setId(int id) {

        this.id = id;
    }
    public long getId() {

        return this.id;
    }
    public String getPergunta() {

        return this.pergunta;
    }
    public int getImagem() {

        return this.imagem;
    }
    public String[] getRespostas() {

        return this.respostas;
    }

    public String getRespostaCerta() {

        return this.respostaCerta;
    }

    public String toString() {
        String retorno = "";

        retorno += this.pergunta + "\r\n" +
                this.respostas[0] + "\r\n" +
                this.respostas[1] + "\r\n" +
                this.respostas[2] + "\r\n" +
                this.respostas[3] + "\r\n";

        return retorno;
    }
}
