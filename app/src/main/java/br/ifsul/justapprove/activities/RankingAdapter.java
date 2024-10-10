package br.ifsul.justapprove.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ifsul.justapprove.R;
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
        holder.imgPerfil.setImageResource(R.drawable.perfil);
        holder.apelido.setText(usuario.getApelido());
        holder.pontuacao.setText(Integer.toString(usuario.getPonto()));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

}
