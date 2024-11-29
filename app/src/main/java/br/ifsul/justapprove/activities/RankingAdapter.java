package br.ifsul.justapprove.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private OnClickListener listener;

    public RankingAdapter(List<Usuario> listaUsuarios, OnClickListener listener) {
        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
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
        if(usuario.getImage() == null){
            holder.imgPerfil.setImageResource(R.drawable.perfil);
        }
        else {
            byte[] imagem = java.util.Base64.getDecoder().decode(usuario.getImage());
            Bitmap bMap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
            holder.imgPerfil.setImageBitmap(bMap);
        }
        holder.apelido.setText(usuario.getApelido());
        holder.pontuacao.setText(Integer.toString(usuario.getPontos()));

        holder.itemView.setOnClickListener(view -> {
            if(listener != null){
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }
    Usuario getItem(int position){
        return listaUsuarios.get(position);
    }
}
