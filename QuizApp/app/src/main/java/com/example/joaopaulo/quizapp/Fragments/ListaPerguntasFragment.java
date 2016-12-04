package com.example.joaopaulo.quizapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.joaopaulo.quizapp.Adapters.ListaPerguntasAdapter;
import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.PrincipalActivity;
import com.example.joaopaulo.quizapp.R;

import java.util.ArrayList;
import java.util.List;


public class ListaPerguntasFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ListaPerguntasFragment() {}

    public static ListaPerguntasFragment newInstance(String param1, String param2) {
        ListaPerguntasFragment fragment = new ListaPerguntasFragment();
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

        View v = inflater.inflate(R.layout.fragment_lista_perguntas, container, false);

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.list_items);
        final ListaPerguntasAdapter listaPerguntasAdapter =  new ListaPerguntasAdapter((PrincipalActivity) mListener, retornaListaPerguntas());

        Button btnFechar = (Button) v.findViewById(R.id.btn_fechar_lista);

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fechaLista();
            }
        });

        recyclerView.setAdapter(listaPerguntasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

    private List<Perguntas> retornaListaPerguntas() {

        List<Perguntas> lista;

        if (mListener != null) {
           lista = mListener.pegaListaDePerguntas();
        } else {
            lista = new ArrayList<Perguntas>();
        }
        return lista;
    }

    private void fechaLista() {
        if (mListener != null) {
            mListener.terminaVisualizacaoLista(this);
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

        void terminaVisualizacaoLista(ListaPerguntasFragment listaPerguntasFragment);
        List<Perguntas> pegaListaDePerguntas();
    }
}
