package com.example.joaopaulo.quizapp.Data;

import android.content.SharedPreferences;

/**
 * Created by Joao Paulo on 30/11/2016.
 */

public class PreferenciasCompartilhadas {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;


    public PreferenciasCompartilhadas(SharedPreferences settings) {

        this.settings = settings;
        editor = settings.edit();
    }

    public String pegaPreferencia(String propriedade, String valorDefault) {

        return settings.getString(propriedade, valorDefault);
    }

    public void atualizaPreferencia(String propriedade, String valor) {

        editor.putString(propriedade, valor);
        editor.commit();
    }
/*
    settings = getSharedPreferences(String.valueOf(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
    String defaultNome = getString(R.string.nome_usuario_default);
    nomeUsuario = settings.getString(getString(R.string.shared_preference_1), defaultNome);
    String defaultNivel = getString(R.string.level_default);
    levelUsuario = settings.getString(getString(R.string.shared_preference_2), defaultNivel);

    settings = getActivity().getSharedPreferences(String.valueOf(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
    editor = settings.edit();
    editor.putString(getString(R.string.shared_preference_1), nomeUsuario);
    editor.putString(getString(R.string.shared_preference_2), levelUsuario);
    editor.commit(); */
}
