package com.example.joaopaulo.quizapp.Data;

import android.provider.BaseColumns;

/**
 * Created by Joao Paulo on 30/11/2016.
 */

public class DataBaseContract implements BaseColumns {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "JQuiz.db";
    public static final String TABLE_PERGUNTAS = "perguntas";
    public static final String COLUMN_IMAGEM = "imagem";
    public static final String COLUMN_PERGUNTA = "pergunta";
    public static final String COLUMN_ALTERNATIVA_1 = "alternativaA";
    public static final String COLUMN_ALTERNATIVA_2 = "alternativaB";
    public static final String COLUMN_ALTERNATIVA_3 = "alternativaC";
    public static final String COLUMN_ALTERNATIVA_4 = "alternativaD";
    public static final String COLUMN_RESPOSTA = "resposta";

    private DataBaseContract() {}

    public static String retornaCriacaoTabelaPerguntas() {

        return "CREATE TABLE " + TABLE_PERGUNTAS + "( " +
                _ID  + " INTEGER PRIMARY KEY, " +
                COLUMN_IMAGEM + " INTEGER, " +
                COLUMN_PERGUNTA + " TEXT, " +
                COLUMN_ALTERNATIVA_1 + " TEXT, " +
                COLUMN_ALTERNATIVA_2 + " TEXT, " +
                COLUMN_ALTERNATIVA_3 + " TEXT, " +
                COLUMN_ALTERNATIVA_4 + " TEXT, " +
                COLUMN_RESPOSTA + " TEXT )";
    }

    public static String retornaDropTabelaPerguntas() {

        return "DROP TABLE IF EXISTS " + TABLE_PERGUNTAS;
    }

    public static String[] retornaCampoListaPerguntas() {

        return new String[] {_ID};
    }

    public static String[] retornaCamposSelecao() {

        return new String[] {_ID, COLUMN_IMAGEM, COLUMN_PERGUNTA, COLUMN_ALTERNATIVA_1,
                COLUMN_ALTERNATIVA_2, COLUMN_ALTERNATIVA_3, COLUMN_ALTERNATIVA_4, COLUMN_RESPOSTA};
    }

}
