package br.ifsul.justapprove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingAdapter extends BaseAdapter {
    private static ArrayList<RankingAdicionar> rankingArrayList;

    private LayoutInflater mInflater;

    public RankingAdapter(Context context, ArrayList<RankingAdicionar> results) {
        rankingArrayList = results;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return rankingArrayList.size();
    }

    public Object getItem(int position) {
        return rankingArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lista_ranking, null);
            holder = new ViewHolder();
            holder.imgPerfil = (ImageView) convertView.findViewById(R.id.perfil);
            holder.txtApelido = (TextView) convertView.findViewById(R.id.apelido);
            holder.txtPontuacao = (TextView) convertView.findViewById(R.id.pontuacao);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.imgPerfil.setText(rankingArrayList.get(position).getPerfilImagem());
        holder.imgPerfil.setImageResource(R.drawable.teste);
        holder.txtApelido.setText(rankingArrayList.get(position).getApelido());
        holder.txtPontuacao.setText(rankingArrayList.get(position).getPontuacao());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgPerfil;
        TextView txtApelido;
        TextView txtPontuacao;
    }
}
