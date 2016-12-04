package com.example.joaopaulo.quizapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaopaulo.quizapp.Data.Perguntas;
import com.example.joaopaulo.quizapp.PrincipalActivity;
import com.example.joaopaulo.quizapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao Paulo on 01/12/2016.
 */

public class ListaPerguntasAdapter extends RecyclerView.Adapter<ListaPerguntasAdapter.ItemViewHolder>{

    PrincipalActivity inicialActivity;
    List<Perguntas> perguntas = new ArrayList<>();

    public ListaPerguntasAdapter(PrincipalActivity inicialActivity, List<Perguntas> listaPerguntas) {
        this.inicialActivity = inicialActivity;
        perguntas = listaPerguntas;
    }

    public void adicionaNovaPergunta(Perguntas novaPergunta){
        perguntas.add(novaPergunta);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pergunta, parent, false);
        return new ItemViewHolder(inicialActivity, view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Perguntas pAtual = perguntas.get(position);
        holder.atualizaInformacoesDoItem(pAtual);
    }

    @Override
    public int getItemCount() {
        return perguntas.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        Perguntas pergunta;
        private final PrincipalActivity inicialActivity;

        public ItemViewHolder(final PrincipalActivity inicialActivity, View view) {

            super(view);

            this.inicialActivity = inicialActivity;
            setImageView((ImageView) view.findViewById(R.id.img_cardview));
            setTextView((TextView) view.findViewById(R.id.txt_cardview));

            view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            inicialActivity.verificaPerguntaSelecionada(pergunta);
                                        }
                                    }
            );

        }


        public void setPergunta(Perguntas pergunta) {
            this.pergunta = pergunta;
        }
        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
        public ImageView getImageView() {
            return this.imageView;
        }
        public void setTextView(TextView textView) {
            this.textView = textView;
        }
        public void atualizaInformacoesDoItem(Perguntas pergunta) {

            setPergunta(pergunta);
            atualizaImagemPergunta(pergunta);
            atualizaTextoPergunta(pergunta);
        }
        private void atualizaImagemPergunta(Perguntas pergunta) {

            inicialActivity.visualizaImagemPergunta(getImageView(), pergunta.getImagem());
        }
        private void atualizaTextoPergunta(Perguntas pergunta) {

            textView.setText(String.valueOf(pergunta.getPergunta()));
        }
    }
}
