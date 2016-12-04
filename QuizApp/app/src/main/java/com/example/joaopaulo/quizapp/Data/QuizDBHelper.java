package com.example.joaopaulo.quizapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao Paulo on 30/11/2016.
 */

public class QuizDBHelper extends SQLiteOpenHelper {

    public QuizDBHelper(Context context) {
        super(context, DataBaseContract.DATABASE_NAME, null, DataBaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLDB) {

        sqLDB.execSQL(DataBaseContract.retornaCriacaoTabelaPerguntas());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLDB, int i, int i1) {

        sqLDB.execSQL(DataBaseContract.retornaDropTabelaPerguntas());

        onCreate(sqLDB);
    }

    public boolean verificaPreenchimentoPerguntasPadrao(Perguntas pergunta) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataBaseContract.TABLE_PERGUNTAS, DataBaseContract.retornaCamposSelecao(), DataBaseContract.COLUMN_PERGUNTA + "=?",
                new String[] { String.valueOf(pergunta.getPergunta()) }, null, null, null, null);

        boolean existePergunta = false;

        if (cursor != null) {

            existePergunta = cursor.getCount() > 0;

            cursor.close();
        }

        return existePergunta;
    }

    public long adicionarPergunta(Perguntas perguntas) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.COLUMN_IMAGEM, perguntas.imagem);
        values.put(DataBaseContract.COLUMN_PERGUNTA, perguntas.pergunta);
        values.put(DataBaseContract.COLUMN_ALTERNATIVA_1, perguntas.respostas[0]);
        values.put(DataBaseContract.COLUMN_ALTERNATIVA_2, perguntas.respostas[1]);
        values.put(DataBaseContract.COLUMN_ALTERNATIVA_3, perguntas.respostas[2]);
        values.put(DataBaseContract.COLUMN_ALTERNATIVA_4, perguntas.respostas[3]);
        values.put(DataBaseContract.COLUMN_RESPOSTA, perguntas.respostaCerta);

        long novoId = db.insert(DataBaseContract.TABLE_PERGUNTAS, null, values);
        db.close();

        return novoId;
    }

    public Perguntas buscarPergunta(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DataBaseContract.TABLE_PERGUNTAS, DataBaseContract.retornaCamposSelecao(), DataBaseContract._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        Perguntas p = new Perguntas(cursor.getInt(1), cursor.getString(2),
                new String[] {cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)},
                cursor.getString(7), cursor.getLong(0));

        cursor.close();

        return p;
    }

    public List<Perguntas> buscarTodasPerguntas() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DataBaseContract.TABLE_PERGUNTAS, DataBaseContract.retornaCamposSelecao(),
                null, null, null, null, null);

        List<Perguntas> lp = new ArrayList<Perguntas>();

        if (cursor.moveToFirst()) {
            do {

                Perguntas p = new Perguntas(cursor.getInt(1), cursor.getString(2),
                        new String[] {cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)},
                        cursor.getString(7), cursor.getLong(0));

                lp.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return lp;
    }

}
