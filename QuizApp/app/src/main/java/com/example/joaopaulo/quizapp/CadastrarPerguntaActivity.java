package com.example.joaopaulo.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.joaopaulo.quizapp.Data.Perguntas;

public class CadastrarPerguntaActivity extends AppCompatActivity {

    private int radioButtonSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pergunta);

        radioButtonSelecionado = -1;
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_cadastro_perguntas);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                radioButtonSelecionado = i;
            }
        });

        Button bg = (Button) findViewById(R.id.btn_gravar);

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCadastroDaPergunta();
            }
        });

    }

    private void verificaCadastroDaPergunta() {

        String mensagem = "";

        String pergunta = ((EditText)findViewById(R.id.edt_cadastro_pergunta)).getText().toString();
        String alternativa1 = ((EditText)findViewById(R.id.edt_cadastro_alternativa_1)).getText().toString();
        String alternativa2 = ((EditText)findViewById(R.id.edt_cadastro_alternativa_2)).getText().toString();
        String alternativa3 = ((EditText)findViewById(R.id.edt_cadastro_alternativa_3)).getText().toString();
        String alternativa4 = ((EditText)findViewById(R.id.edt_cadastro_alternativa_4)).getText().toString();
        String respostaCerta = "";

        switch(radioButtonSelecionado) {
            case R.id.rbt_cadastro_alternativa_1 : respostaCerta = alternativa1;
                break;
            case R.id.rbt_cadastro_alternativa_2 : respostaCerta = alternativa2;
                break;
            case R.id.rbt_cadastro_alternativa_3 : respostaCerta = alternativa3;
                break;
            case R.id.rbt_cadastro_alternativa_4 : respostaCerta = alternativa4;
                break;
        }

        if (radioButtonSelecionado == -1) {

            mensagem += "Não foi informada a alternativa correta.\r\n";
        }
        if (pergunta == "" || pergunta.isEmpty()) {

            mensagem += "Não foi informada uma pergunta para cadastrar no J Quiz.\r\n";
        }
        if (alternativa1 == "" || alternativa1.isEmpty()) {

            mensagem += "Não foi informada a primeira alternativa para cadastrar no J Quiz.\r\n";
        }
        if (alternativa2 == "" || alternativa2.isEmpty()) {

            mensagem += "Não foi informada a segunda alternativa para cadastrar no J Quiz.\r\n";
        }
        if (alternativa3 == "" || alternativa3.isEmpty()) {

            mensagem += "Não foi informada a terceira alternativa para cadastrar no J Quiz.\r\n";
        }
        if (alternativa4 == "" || alternativa4.isEmpty()) {

            mensagem += "Não foi informada a quarta alternativa para cadastrar no J Quiz.\r\n";
        }

        if (mensagem == "" || mensagem.isEmpty()) {

            String[] respostas = {alternativa1, alternativa2, alternativa3, alternativa4};

            finalizaCadastroPergunta(new Perguntas(R.drawable.img_default, pergunta,
                    respostas, respostaCerta));

        } else {

            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
        }
    }

    private void finalizaCadastroPergunta(Perguntas pergunta) {

        Bundle b = new Bundle();
        b.putSerializable("novaPergunta", pergunta);

        Intent intent = new Intent();
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
        finish();
    }
}
