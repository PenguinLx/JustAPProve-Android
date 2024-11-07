package br.ifsul.justapprove.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ifsul.justapprove.R;

public class MateriaHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    public MateriaHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.id_titulo_material);
        imageView = itemView.findViewById(R.id.list_material_imagem_id);
    }
}
