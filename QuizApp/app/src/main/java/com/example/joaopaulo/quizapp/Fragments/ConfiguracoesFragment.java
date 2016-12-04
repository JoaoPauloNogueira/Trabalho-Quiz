package com.example.joaopaulo.quizapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.joaopaulo.quizapp.R;

public class ConfiguracoesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ConfiguracoesFragment() {}

    public static ConfiguracoesFragment newInstance() {
        ConfiguracoesFragment fragment = new ConfiguracoesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        Button bl = (Button) v.findViewById(R.id.btn_listar);
        Button bn = (Button) v.findViewById(R.id.btn_incluir);

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visualizaListaPerguntas();
            }
        });
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                criarNovaPergunta();
            }
        });

        return v;
    }

    public void visualizaListaPerguntas() {
        if (mListener != null) {
            mListener.verificaListaPerguntas();
        }
    }
    public void criarNovaPergunta() {
        if (mListener != null) {
            mListener.criaNovaPergunta();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void verificaListaPerguntas();
        void criaNovaPergunta();
    }
}
