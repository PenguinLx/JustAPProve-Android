package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import br.ifsul.justapprove.R;

public class PdfActivity extends AppCompatActivity {
    PDFView pdfView;
    ImageButton botaoVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView = findViewById(R.id.pdf_view);
        botaoVoltar = findViewById(R.id.botao_pdf_voltar_id);

        Intent i = getIntent();
        i.getExtras();
        String pdfFilePathString = i.getStringExtra("pdfFilePath");

        pdfView.fromFile(new File(pdfFilePathString)).load();

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);

                startActivity(i);
                finish();
            }
        });
    }



}