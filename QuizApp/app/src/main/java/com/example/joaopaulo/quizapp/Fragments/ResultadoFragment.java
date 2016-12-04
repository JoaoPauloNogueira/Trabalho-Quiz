package com.example.joaopaulo.quizapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.joaopaulo.quizapp.R;

public class ResultadoFragment extends Fragment {

    private static final String ARG_NOME_USUARIO = "nomeUsuario";
    private static final String ARG_LEVEL_USUARIO = "levelUsuario";
    private static final String ARG_RESPOSTAS_CERTAS = "respostasCertas";
    private static final String ARG_ORIGEM = "origem";

    private String mNomeUsuario;
    private String mLevelUsuario;
    private int mRespostasCertas;
    private int mOrigem;

    private OnFragmentInteractionListener mListener;

    public ResultadoFragment() {}

    public static ResultadoFragment newInstance(String nomeUsuario, String levelUsuario, int respostasCertas, int origem) {
        ResultadoFragment fragment = new ResultadoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOME_USUARIO, nomeUsuario);
        args.putString(ARG_LEVEL_USUARIO, levelUsuario);
        args.putInt(ARG_RESPOSTAS_CERTAS, respostasCertas);
        args.putInt(ARG_ORIGEM, origem);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNomeUsuario = getArguments().getString(ARG_NOME_USUARIO);
            mLevelUsuario = getArguments().getString(ARG_LEVEL_USUARIO);
            mRespostasCertas = getArguments().getInt(ARG_RESPOSTAS_CERTAS);
            mOrigem = getArguments().getInt(ARG_ORIGEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);

        TextView tv = (TextView) view.findViewById(R.id.txt_resultado);

        tv.setText("Nível: " + mLevelUsuario + "\r\n" +
                "Jogador(a) " + mNomeUsuario + "\r\n" +
                "Respostas certas: " + mRespostasCertas);

        Button bOk = (Button) view.findViewById(R.id.btn_ok);

        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizaOQuiz();
            }
        });

        return view;
    }

    public void finalizaOQuiz() {
        if (mListener != null) {
            mListener.fechaResultado(this);
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

        void fechaResultado( ResultadoFragment resultadoFragment);
    }
}
