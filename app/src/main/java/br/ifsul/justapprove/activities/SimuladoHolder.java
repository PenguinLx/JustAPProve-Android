package br.ifsul.justapprove.activities;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.ifsul.justapprove.R;

public class SimuladoHolder extends RecyclerView.ViewHolder {

    private WebView pdf;
    private Button pdfButton;



    public SimuladoHolder(@NonNull View itemView) {
        super(itemView);
        pdf = itemView.findViewById(R.id.pdf_wv_id);
        pdfButton =itemView.findViewById(R.id.botao_pdf_voltar_id);
    }
}
