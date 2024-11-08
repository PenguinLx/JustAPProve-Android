package br.ifsul.justapprove.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaHolder> {
    private List<Materia> materiaList;
    private OnClickListener listener;


    public MateriaAdapter(List<Materia> materiaList, OnClickListener listener) {
        this.materiaList = materiaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MateriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_materia, parent, false);
        return new MateriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaHolder holder, int position) {
        Materia materia = materiaList.get(position);
        holder.imageView.setImageResource(R.drawable.seta);
        holder.textView.setText(materia.getNome());

        holder.itemView.setOnClickListener(view -> {
            if(listener != null){
                listener.onClick(position);
            }

        });
    }

    @Override
    public int getItemCount() {
        return materiaList.size();
    }



    Materia getItem(int position){
        return materiaList.get(position);
    }

}
