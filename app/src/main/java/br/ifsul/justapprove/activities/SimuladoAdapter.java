package br.ifsul.justapprove.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.ProvaAnterior;

public class SimuladoAdapter extends ArrayAdapter<ProvaAnterior> {

    private List<ProvaAnterior> provas;


    public SimuladoAdapter(@NonNull Context context, List<ProvaAnterior> provaList) {
        super(context, 0, provaList);
        this.provas = provaList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.cardview_prova, parent, false);
        }
        ProvaAnterior provaAnterior = getItem(position);
        ImageView imageView = listitemView.findViewById(R.id.cardV_imageV_id);
        TextView textView = listitemView.findViewById(R.id.cardV_text_view_id);
        CardView cardView = listitemView.findViewById(R.id.id_cardview);


        imageView.setImageResource(R.drawable.simulados);
        textView.setText(provaAnterior.getTitulo());
        cardView.setTag(provaAnterior.getPdf());
        return listitemView;
    }

}
