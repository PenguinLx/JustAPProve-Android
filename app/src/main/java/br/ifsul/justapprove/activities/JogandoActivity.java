package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.retrofit.QuestaoApi;

public class JogandoActivity extends AppCompatActivity {
    private TextView questaoText,pontos;
    private ImageView questaoImage;
    private Button a1,a2,a3,a4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogando);
        questaoText = findViewById(R.id.questao_text);
        questaoImage = findViewById(R.id.questao_image);
        pontos = findViewById(R.id.pontuacao);
        a1 = findViewById(R.id.resposta1);
        a2 = findViewById(R.id.resposta2);
        a3 = findViewById(R.id.resposta3);
        a4 = findViewById(R.id.resposta4);
        Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
        ArrayList<Questao> questoes = getIntent().getParcelableArrayListExtra("listQuestao");
        QuestaoApi questaoApi;
        i.getExtras();
        if(questoes != null ){
            finish();

        }
    }
}