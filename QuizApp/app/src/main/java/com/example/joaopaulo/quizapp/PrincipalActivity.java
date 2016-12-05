package com.example.joaopaulo.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.joaopaulo.quizapp.Adapters.ViewPagerSelecaoAdapter;
import com.example.joaopaulo.quizapp.Data.ListaPerguntas;
import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.Data.PreferenciasCompartilhadas;
import com.example.joaopaulo.quizapp.Data.QuizDBHelper;
import com.example.joaopaulo.quizapp.Fragments.ConfiguracoesFragment;
import com.example.joaopaulo.quizapp.Fragments.IniciarFragment;
import com.example.joaopaulo.quizapp.Fragments.ListaPerguntasFragment;
import com.example.joaopaulo.quizapp.Fragments.PerguntasFragment;
import com.example.joaopaulo.quizapp.Fragments.ResultadoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PrincipalActivity extends AppCompatActivity implements IniciarFragment.OnFragmentInteractionListener,
        ConfiguracoesFragment.OnFragmentInteractionListener,
        ListaPerguntasFragment.OnFragmentInteractionListener,
        PerguntasFragment.OnFragmentInteractionListener,
        ResultadoFragment.OnFragmentInteractionListener {

    private static int RESULTADO_QUIZ = 10;
    private static int NOVA_PERGUNTA = 20;

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

        inicializaFramesLayouts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == RESULTADO_QUIZ) {

                Bundle b = data.getExtras();

                ResultadoFragment fAtual = ResultadoFragment.newInstance(b.getString("nomeDoUsuario"),
                        b.getString("nivelDoUsuario"), b.getInt("qtdRespostasCertas"), ResultadoFragment.ORIGEM_QUIZ);

                atualizaVisibilidadeDoFrameLayout(fFragments, View.VISIBLE);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frm_fragment, fAtual);
                ft.commit();

            } else if (requestCode == NOVA_PERGUNTA) {

                Bundle b = data.getExtras();
                Perguntas pergunta = (Perguntas) b.get("novaPergunta");

                quizDB.adicionarPergunta(pergunta);

                ResultadoFragment fAtual = ResultadoFragment.newInstance(nomeUsuario,
                        levelUsuario, 0, ResultadoFragment.ORIGEM_CADASTRO_PERGUNTA);

                atualizaVisibilidadeDoFrameLayout(fFragments, View.VISIBLE);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frm_fragment, fAtual);
                ft.commit();
            }
        }
    }

    /**
     *  Métodos relacionados aos controles dos fragments
     * */

    //-- IniciarFragment
    @Override
    public void inicializaJQuiz(String usuario, String nivel) {

        preferenciasCompartilhadas.atualizaPreferencia(getString(R.string.shared_preference_1), usuario);
        preferenciasCompartilhadas.atualizaPreferencia(getString(R.string.shared_preference_2), nivel);

        List<Perguntas> p = selecionaPerguntas(nivel);
        int quantidadePerguntas = p.size();

        ListaPerguntas lp = new ListaPerguntas(quantidadePerguntas, nivel, usuario, p);

        Intent intQuiz = new Intent(PrincipalActivity.this, JQuizActivity.class);
        intQuiz.putExtra("listaPerguntas", lp);
        startActivityForResult(intQuiz, RESULTADO_QUIZ);
    }

    //-- ListaPerguntasFragment
    @Override
    public void terminaVisualizacaoLista(ListaPerguntasFragment listaPerguntasFragment) {

        atualizaVisibilidadeDoFrameLayout(fFragments, View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(listaPerguntasFragment);
        ft.commit();
    }
    @Override
    public List<Perguntas> pegaListaDePerguntas() {

        return quizDB.buscarTodasPerguntas();
    }

    //-- ConfiguracoesFragment
    @Override
    public void verificaListaPerguntas() {

        ListaPerguntasFragment fAtual = new ListaPerguntasFragment();

        atualizaVisibilidadeDoFrameLayout(fFragments, View.VISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frm_fragment, fAtual);
        ft.commit();
    }
    @Override
    public void criaNovaPergunta() {

        Intent intQuiz = new Intent(PrincipalActivity.this, CadastrarPerguntaActivity.class);
        startActivityForResult(intQuiz, NOVA_PERGUNTA);
    }

    //-- PerguntasFragment
    @Override
    public void verificaRespostaSelecionada(boolean respostaCerta) {}

    @Override
    public void terminaVisualizacaoPergunta(PerguntasFragment perguntasFragment) {

        atualizaVisibilidadeDoFrameLayout(fFragments2, View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(perguntasFragment);
        ft.commit();
    }

    //-- ResultadoFragment
    @Override
    public void fechaResultado(ResultadoFragment resultadoFragment) {

        atualizaVisibilidadeDoFrameLayout(fFragments, View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(resultadoFragment);
        ft.commit();
    }

    //-- ListaPerguntasAdapter
    public void verificaPerguntaSelecionada(Perguntas pergunta) {

        PerguntasFragment fAtual = new PerguntasFragment();

        fAtual.setPergunta(pergunta);
        fAtual.setApenasVisualiza(true);

        atualizaVisibilidadeDoFrameLayout(fFragments2, View.VISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frm_fragment2, fAtual);
        ft.commit();
    }

    /**
     * Métodos auxuliares
     */

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

        for (int i = 0; i < imagens.length(); i++) {

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


        final RadioGroup rgMenu = (RadioGroup) findViewById(R.id.rg_opcoes_menu);
        final ViewPager vpSelecao = (ViewPager) findViewById(R.id.vp_selecao);
        ViewPagerSelecaoAdapter vpaSelecao = new ViewPagerSelecaoAdapter(pegaListaFragments(), getSupportFragmentManager());

        vpSelecao.setAdapter(vpaSelecao);
        vpSelecao.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {

                vpSelecao.setCurrentItem(position);

                if (position == 0) {

                    ((RadioButton)rgMenu.findViewById(R.id.rbt_menu_iniciar)).setChecked(true);

                } else if ( position == 1) {

                    ((RadioButton)rgMenu.findViewById(R.id.rbt_menu_configuracoes)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i != -1) {

                    if (i == R.id.rbt_menu_iniciar) {

                        vpSelecao.setCurrentItem(0);

                    } else if (i == R.id.rbt_menu_configuracoes) {

                        vpSelecao.setCurrentItem(1);
                    }
                }
            }
        });
    }
    private List<Fragment> pegaListaFragments() {

        List<Fragment> lista = new ArrayList<Fragment>();
        IniciarFragment f = IniciarFragment.newInstance(nomeUsuario, levelUsuario);

        lista.add(f);

        lista.add(new ConfiguracoesFragment());

        return lista;
    }

    private void inicializaFramesLayouts() {

        fFragments = (FrameLayout) findViewById(R.id.frm_fragment);
        fFragments2 = (FrameLayout) findViewById(R.id.frm_fragment2);
    }

    private void atualizaVisibilidadeDoFrameLayout(FrameLayout fLayout, int visibilidade) {

        fLayout.setVisibility(visibilidade);
    }

    private List<Perguntas> selecionaPerguntas(String nivel) {

        List<Perguntas> lp = quizDB.buscarTodasPerguntas();

        int totalPerguntas = lp.size();
        int[] perguntasSelecionadas = new int[15];

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
