package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.justapprove.R;

public class PdfActivity extends AppCompatActivity {
//https://www.geeksforgeeks.org/how-to-open-pdf-from-url-in-android-without-any-third-party-libraries/
    WebView webView;
    String pdfUrl = "https://apnp.ifsul.edu.br/pluginfile.php/1274333/mod_resource/content/1/ES%20-%20Diagrama%20de%20Classes.pdf";
    Button botaoVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        webView = findViewById(R.id.pdf_wv_id);
        botaoVoltar = findViewById(R.id.botao_pdf_voltar_id);

        Intent i = getIntent();
        i.getExtras();
        String pdfLink = i.getStringExtra("tagPdf");
        public void viewPdf(){

            
        }

    }


}