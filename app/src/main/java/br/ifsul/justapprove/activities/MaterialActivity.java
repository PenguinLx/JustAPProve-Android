package br.ifsul.justapprove.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.justapprove.R;

public class MaterialActivity extends AppCompatActivity {

    private TextView descricao;
    private WebView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        descricao = findViewById(R.id.descricao);
        video = findViewById(R.id.video);


    }



}