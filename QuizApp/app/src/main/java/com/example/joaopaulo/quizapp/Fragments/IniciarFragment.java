package com.example.joaopaulo.quizapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joaopaulo.quizapp.R;

public class IniciarFragment extends Fragment {

    private static final String ARG_NOME_USUARIO = "nomeUsuario";
    private static final String ARG_LEVEL_USUARIO = "levelUsuario";

    private String mNomeUsuario;
    private String mLevelUsuario;

    private OnFragmentInteractionListener mListener;

    public IniciarFragment() { }

    public static IniciarFragment newInstance(String nomeUsuario, String levelUsuario) {
        IniciarFragment fragment = new IniciarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOME_USUARIO, nomeUsuario);
        args.putString(ARG_LEVEL_USUARIO, levelUsuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNomeUsuario = getArguments().getString(ARG_NOME_USUARIO);
            mLevelUsuario = getArguments().getString(ARG_LEVEL_USUARIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_iniciar, container, false);

        Spinner opcoesNivel = (Spinner) v.findViewById(R.id.spn_nivel);

        ArrayAdapter<CharSequence> opcoesAdapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.opcoes_level, android.R.layout.simple_spinner_item);
        opcoesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opcoesNivel.setAdapter(opcoesAdapter);

        final EditText et = (EditText) v.findViewById(R.id.edt_nome);
        et.setText(mNomeUsuario);

        final Spinner sp = (Spinner) v.findViewById(R.id.spn_nivel);
        sp.setSelection(opcoesAdapter.getPosition(mLevelUsuario));

        Button bi = (Button) v.findViewById(R.id.btn_iniciar);

        bi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String nomeUsuario = et.getText().toString();
                String levelUsuario = sp.getSelectedItem().toString();

                if (nomeUsuario == "" || nomeUsuario.isEmpty()) {

                    Toast.makeText(getActivity(), "O nome do usuário deve ser digitado para poder começar o J Quiz.", Toast.LENGTH_LONG).show();

                } else {

                    SharedPreferences settings = getActivity().getSharedPreferences(String.valueOf(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(getString(R.string.shared_preference_1), nomeUsuario);
                    editor.putString(getString(R.string.shared_preference_2), levelUsuario);
                    editor.commit();

                    iniciarQuiz(nomeUsuario, levelUsuario);
                }
            }
        });

        return v;
    }

    public void iniciarQuiz(String usuario, String nivel) {
        if (mListener != null) {
            mListener.inicializaJQuiz(usuario, nivel);
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

        void inicializaJQuiz(String usuario, String nivel);
    }
}
