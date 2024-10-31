package br.ifsul.justapprove.activities;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import br.ifsul.justapprove.models.ProvaAnterior;
import br.ifsul.justapprove.models.Simulado;

public class SimuladoAdapter extends ArrayAdapter<ProvaAnterior> {

    List<ProvaAnterior> provas;

    public SimuladoAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }



}
