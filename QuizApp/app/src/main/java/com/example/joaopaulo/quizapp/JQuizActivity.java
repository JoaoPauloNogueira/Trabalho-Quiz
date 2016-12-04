package com.example.joaopaulo.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joaopaulo.quizapp.Data.ListaPerguntas;
import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.Fragments.PerguntasFragment;

import java.util.List;

public class JQuizActivity extends AppCompatActivity implements PerguntasFragment.OnFragmentInteractionListener{

    private int respostasCertas;
    private List<Perguntas> perguntas;
    private String nivel;
    private String usuario;
    private int quantidadePerguntas;
    private int perguntasRealizadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jquiz);

        respostasCertas = 0;
        perguntasRealizadas = 0;

        Bundle b = getIntent().getExtras();
        ListaPerguntas l = (ListaPerguntas) b.getSerializable("listaPerguntas");
        quantidadePerguntas = l.getQuantidadePerguntas();
        nivel = l.getNivel();
        usuario = l.getUsuario();
        perguntas = l.getPerguntas();

        criaPergunta();
    }

    private void criaPergunta() {

        if (perguntasRealizadas >= quantidadePerguntas) {

            finalizaQuiz();

        } else {

            PerguntasFragment fAtual = new PerguntasFragment();

            Perguntas p = perguntas.get(perguntasRealizadas);

            perguntasRealizadas++;

            fAtual.setApenasVisualiza(false);
            fAtual.setPergunta(p);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frm_perguntas, fAtual);
            ft.commit();
        }
    }

    private void finalizaQuiz() {

        Bundle b = new Bundle();
        b.putString("nomeDoUsuario", usuario);
        b.putString("nivelDoUsuario", nivel);
        b.putInt("qtdRespostasCertas", respostasCertas);
        Intent intent = new Intent();
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void visualizaImagemPergunta(ImageView iv, int imagem) {

        if (iv != null) {
            iv.setImageDrawable(getResources().getDrawable(imagem));
        }
    }

    @Override
    public void verificaRespostaSelecionada(boolean respostaCerta) {

        if (respostaCerta) {

            respostasCertas++;
            Toast.makeText(this, "Resposta certa!", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Resposta errada :(", Toast.LENGTH_SHORT).show();
        }
        criaPergunta();
    }

    @Override
    public void terminaVisualizacaoPergunta(PerguntasFragment perguntasFragment) {

    }
}
