package com.example.joaopaulo.quizapp.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.JQuizActivity;
import com.example.joaopaulo.quizapp.PrincipalActivity;
import com.example.joaopaulo.quizapp.R;

public class PerguntasFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Perguntas pergunta;
    private String respostaCerta;
    private boolean apenasVisualiza;

    public PerguntasFragment() {}

    public static PerguntasFragment newInstance(String param1, String param2) {
        PerguntasFragment fragment = new PerguntasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPergunta(Perguntas pergunta) {

        this.pergunta = pergunta;
    }
    public void setApenasVisualiza(boolean apenasVisualiza) {

        this.apenasVisualiza = apenasVisualiza;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perguntas, container, false);

        inicializaPergunta(view);

        return view;
    }

    private void inicializaPergunta(View view) {

        Button btnFechar = (Button) view.findViewById(R.id.btn_fechar_pergunta);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fechaPergunta();
            }
        });

        ImageView iv = (ImageView) view.findViewById(R.id.img_pergunta);
        TextView et = (TextView) view.findViewById(R.id.txt_pergunta);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rdg_pergunta);
        RadioButton rb1 = (RadioButton) view.findViewById(R.id.rbt_resposta1);
        RadioButton rb2 = (RadioButton) view.findViewById(R.id.rbt_resposta2);
        RadioButton rb3 = (RadioButton) view.findViewById(R.id.rbt_resposta3);
        RadioButton rb4 = (RadioButton) view.findViewById(R.id.rbt_resposta4);

        iv.setImageDrawable(view.getResources().getDrawable(pergunta.getImagem()));
        et.setText(pergunta.getPergunta());
        String[] respostas = pergunta.getRespostas();

        rb1.setText(respostas[0]);
        rb2.setText(respostas[1]);
        rb3.setText(respostas[2]);
        rb4.setText(respostas[3]);

        respostaCerta = pergunta.getRespostaCerta();

        if (apenasVisualiza) {

            if (respostaCerta.compareTo(respostas[0].toString()) == 0) {

                rb1.setChecked(true);
                rb1.setBackgroundColor(Color.GREEN);
            }
            if (respostaCerta.compareTo(respostas[1].toString()) == 0) {

                rb2.setChecked(true);
                rb2.setBackgroundColor(Color.GREEN);
            }
            if (respostaCerta.compareTo(respostas[2].toString()) == 0) {

                rb3.setChecked(true);
                rb3.setBackgroundColor(Color.GREEN);
            }
            if (respostaCerta.compareTo(respostas[3].toString()) == 0) {

                rb4.setChecked(true);
                rb4.setBackgroundColor(Color.GREEN);
            }
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            rb3.setEnabled(false);
            rb4.setEnabled(false);
            rg.setEnabled(false);

        } else {

           rg.setEnabled(true);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    RadioButton rb = (RadioButton) radioGroup.findViewById(i);

                    realizaEscolha(rb.getText().toString());
                }
            });

            btnFechar.setVisibility(View.INVISIBLE);
        }
    }

    public void realizaEscolha(String respostaEscolhida) {

        if (mListener != null) {

            mListener.verificaRespostaSelecionada(respostaCerta.compareTo(respostaEscolhida) == 0);
        }
    }

    private void fechaPergunta() {
        if (mListener != null) {
            mListener.terminaVisualizacaoPergunta(this);
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

        void verificaRespostaSelecionada(boolean respostaCerta);
        void terminaVisualizacaoPergunta(PerguntasFragment perguntasFragment);
    }
}
