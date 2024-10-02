package br.ifsul.justapprove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.ifsul.justapprove.models.Usuario;

public class RankingAdapter extends RecyclerView.Adapter<RankingHolder> {

    private List<Usuario> listaUsuarios;

    public RankingAdapter(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public RankingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_ranking, parent, false);
        return new RankingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.imgPerfil.setImageResource(R.drawable.teste);;
        holder.apelido.setText(usuario.getNome());
        holder.pontuacao.setText(usuario.getPonto());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

}
