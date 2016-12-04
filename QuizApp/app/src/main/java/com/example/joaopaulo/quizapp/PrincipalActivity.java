package com.example.joaopaulo.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.joaopaulo.quizapp.Adapters.ViewPagerSelecaoAdapter;
import com.example.joaopaulo.quizapp.Data.ListaPerguntas;
import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.Data.PreferenciasCompartilhadas;
import com.example.joaopaulo.quizapp.Data.QuizDBHelper;
import com.example.joaopaulo.quizapp.Fragments.ConfiguracoesFragment;
import com.example.joaopaulo.quizapp.Fragments.IniciarFragment;
import com.example.joaopaulo.quizapp.Fragments.ListaPerguntasFragment;
import com.example.joaopaulo.quizapp.Fragments.PerguntasFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PrincipalActivity extends AppCompatActivity implements IniciarFragment.OnFragmentInteractionListener,
        ConfiguracoesFragment.OnFragmentInteractionListener,
        ListaPerguntasFragment.OnFragmentInteractionListener,
        PerguntasFragment.OnFragmentInteractionListener{

    private String nomeUsuario;
    private String levelUsuario;
    private PreferenciasCompartilhadas preferenciasCompartilhadas;
    private QuizDBHelper quizDB;
    private FrameLayout fFragments;
    private FrameLayout fFragments2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializaPreferencias();

        inicializaBancoDeDados();

        inicializaViewPager();
    }

    protected void inicializaPreferencias() {

        //-- Pegando as informações de preferência salvas de execuções anteriores.

        preferenciasCompartilhadas = new PreferenciasCompartilhadas(getSharedPreferences(String.valueOf(R.string.shared_preference_file_key), Context.MODE_PRIVATE));

        nomeUsuario = preferenciasCompartilhadas.pegaPreferencia(getString(R.string.shared_preference_1), getString(R.string.nome_usuario_default));
        levelUsuario = preferenciasCompartilhadas.pegaPreferencia(getString(R.string.shared_preference_2), getString(R.string.level_default));
    }

    private void inicializaBancoDeDados() {

        quizDB = new QuizDBHelper(this);

        Resources res = getResources();
        TypedArray imagens = res.obtainTypedArray(R.array.imagem);
        TypedArray perguntas = res.obtainTypedArray(R.array.pergunta);
        TypedArray respostas1 = res.obtainTypedArray(R.array.resposta1);
        TypedArray respostas2 = res.obtainTypedArray(R.array.resposta2);
        TypedArray respostas3 = res.obtainTypedArray(R.array.resposta3);
        TypedArray respostas4 = res.obtainTypedArray(R.array.resposta4);
        TypedArray respostasCerta = res.obtainTypedArray(R.array.respostaCerta);

        Perguntas p;
        boolean verificaPerguntasPadrao = true;

        for (int i = 0; i < 10; i++) {

            String[] respostas = {respostas1.getString(i),respostas2.getString(i), respostas3.getString(i), respostas4.getString(i)};
            p = new Perguntas(imagens.getResourceId(i, 0), perguntas.getString(i), respostas, respostasCerta.getString(i));


            if (verificaPerguntasPadrao && quizDB.verificaPreenchimentoPerguntasPadrao(p)) {

                break;
            } else {
                verificaPerguntasPadrao = false;
            }
            quizDB.adicionarPergunta(p);
        }
    }

    private void inicializaViewPager() {

        final ViewPager vpSelecao = (ViewPager) findViewById(R.id.vp_selecao);
        ViewPagerSelecaoAdapter vpaSelecao = new ViewPagerSelecaoAdapter(pegaListaFragments(), getSupportFragmentManager());

        vpSelecao.setAdapter(vpaSelecao);
        vpSelecao.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                vpSelecao.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }
    /**
     * Métodos para tratar os fragmentos
     **/
    private List<Fragment> pegaListaFragments() {

        List<Fragment> lista = new ArrayList<Fragment>();
        IniciarFragment f = IniciarFragment.newInstance(nomeUsuario, levelUsuario);

        lista.add(f);

        lista.add(new ConfiguracoesFragment());

        return lista;
    }

    private void atualizaVisibilidadeDoFrameLayout(int visibilidade) {

        if ( fFragments == null) {

            fFragments = (FrameLayout) findViewById(R.id.frm_fragment);
        }
        fFragments.setVisibility(visibilidade);
    }

    private void atualizaVisibilidadeDoFrameLayout2(int visibilidade) {

        if ( fFragments2 == null) {

            fFragments2 = (FrameLayout) findViewById(R.id.frm_fragment2);
        }
        fFragments2.setVisibility(visibilidade);
    }

    public void visualizaImagemPergunta(ImageView iv, int imagem) {

        if (iv != null) {
            iv.setImageDrawable(getResources().getDrawable(imagem));
        }
    }

    /**
     *  Métodos relacionados aos controles dos fragments
     * */

    @Override
    public void verificaListaPerguntas() {

        ListaPerguntasFragment fAtual = new ListaPerguntasFragment();

        atualizaVisibilidadeDoFrameLayout(View.VISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frm_fragment, fAtual);
        ft.commit();
    }

    @Override
    public void terminaVisualizacaoLista(ListaPerguntasFragment listaPerguntasFragment) {

        atualizaVisibilidadeDoFrameLayout(View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(listaPerguntasFragment);
        ft.commit();
    }

    @Override
    public List<Perguntas> pegaListaDePerguntas() {

        return quizDB.buscarTodasPerguntas();
    }

    public void verificaPerguntaSelecionada(Perguntas pergunta) {

        PerguntasFragment fAtual = new PerguntasFragment();

        fAtual.setPergunta(pergunta);
        fAtual.setApenasVisualiza(true);

        atualizaVisibilidadeDoFrameLayout2(View.VISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frm_fragment2, fAtual);
        ft.commit();
    }

    @Override
    public void verificaRespostaSelecionada(boolean respostaCerta) {}

    @Override
    public void terminaVisualizacaoPergunta(PerguntasFragment perguntasFragment) {

        atualizaVisibilidadeDoFrameLayout2(View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(perguntasFragment);
        ft.commit();
    }

    @Override
    public void inicializaJQuiz(String usuario, String nivel) {

        List<Perguntas> p = selecionaPerguntas(nivel);
        int quantidadePerguntas = p.size();

        ListaPerguntas lp = new ListaPerguntas(quantidadePerguntas, nivel, usuario, p);

        Intent intQuiz = new Intent(PrincipalActivity.this, JQuizActivity.class);
        intQuiz.putExtra("listaPerguntas", lp);
        startActivity(intQuiz);
    }

    private List<Perguntas> selecionaPerguntas(String nivel) {

        List<Perguntas> lp = quizDB.buscarTodasPerguntas();

        int totalPerguntas = lp.size();
        int perguntasSelecionadas[] = new int[15];

        int maxPerguntas = 0;
        int i = new Random().nextInt(totalPerguntas);
        int j = 0;

        if (nivel.compareTo(getResources().getString(R.string.level_1)) == 0) {
            maxPerguntas = 5;
        } else if (nivel.compareTo(getResources().getString(R.string.level_2)) == 0) {
            maxPerguntas = 10;
        } else {
            maxPerguntas = 15;
        }

        List<Perguntas> l = new ArrayList<Perguntas>();

        while (j < maxPerguntas) {

            while (Arrays.binarySearch(perguntasSelecionadas, i) >= 0) {

                i = new Random().nextInt(totalPerguntas);
            }
            perguntasSelecionadas[j] = i;
            l.add(lp.get(i));
            j++;
            Arrays.sort(perguntasSelecionadas);
        }

        return l;
    }
}
