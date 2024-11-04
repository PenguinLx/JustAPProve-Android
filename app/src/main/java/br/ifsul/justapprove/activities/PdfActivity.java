package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;

public class PdfActivity extends AppCompatActivity {
//https://www.geeksforgeeks.org/how-to-open-pdf-from-url-in-android-without-any-third-party-libraries/
    String pdf;
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