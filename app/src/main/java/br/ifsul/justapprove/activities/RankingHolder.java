package br.ifsul.justapprove.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ifsul.justapprove.R;

public class RankingHolder extends RecyclerView.ViewHolder {

    TextView apelido,pontuacao;
    ImageView imgPerfil;

    public RankingHolder(@NonNull View itemView) {
        super(itemView);
        apelido = itemView.findViewById(R.id.apelido);
        pontuacao = itemView.findViewById(R.id.pontuacao);
        imgPerfil = itemView.findViewById(R.id.perfil);
    }
}
