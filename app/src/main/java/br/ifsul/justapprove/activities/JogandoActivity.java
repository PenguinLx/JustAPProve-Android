package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.models.Alternativa;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        RetrofitService rfs = new RetrofitService();
        QuestaoApi questaoApi = rfs.getRfs().create(QuestaoApi.class);

        Intent i = getIntent();
        int numero = i.getIntExtra("numero",0);

        questaoApi.gerarSimulado(numero).enqueue(new Callback<List<Questao>>() {
            @Override
            public void onResponse(Call<List<Questao>> call, Response<List<Questao>> response) {
               List<Questao> questList=  response.body();
                byte[] imagem = new byte[0];
                try {
                    imagem = questList.get(1).getDescricao().getBytes(1,101438);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Bitmap bMap = BitmapFactory.decodeByteArray(imagem,0,imagem.length);
                questaoImage.setImageBitmap(bMap);
               //questaoImage.(questList.get(0).getDescricao());
                a1.setText(questList.get(0).getAlternativas().get(0).getDescricao());
        a2.setText(questList.get(0).getAlternativas().get(1).getDescricao());
        a3.setText(questList.get(0).getAlternativas().get(2).getDescricao());
        //a4.setText(questoes.get(0).getAlternativas().get(3).getDescricao());
            }

            @Override
            public void onFailure(Call<List<Questao>> call, Throwable throwable) {
                Toast.makeText(JogandoActivity.this, "Erro ao gerar simulado!", Toast.LENGTH_SHORT).show();
            }
        });
//        if(questoes != null ) {
//            for (int x = 0; x<questoes.size(); x++) {
//                Questao questao = new Questao();
//                questao.setDescricao(questoes.get(x).getDescricao());
//                questao.setAlternativas(questoes.get(x).getAlternativas());
//            }
//        }
//        for (int x = 0; x<questoes.size(); x++) {
//            if (questoes.get(x) != null) {
//                Log.e("Erro", questoes.get(x).getDescricao());
//            }
//        }
//        if (questoes != null) {
//            Log.e("ErroJogando", "questoes " + questoes.size());
//            for (int x = 0; x<questoes.size(); x++) {
//                Questao questao = new Questao();
//                questao.setDescricao(questoes.get(x).getDescricao());
//                if (questao.getDescricao() != null) {
//                    Log.e("ErroJogando", questao.getDescricao());
//                }
//            }
//            for (Questao questao : questoes) {
//                questao.setDescricao(questoes.get(questoes.indexOf(questao)).getDescricao());
////                if (questao.getDescricao() != null) {
////                    Log.e("ErroJogando", questoes.get(questoes.indexOf()).getDescricao());
////                }
//            }
//            Log.e("Erro", questoes.get(0).getDescricao());
        }
//            questoes.get(0).getAlternativas().get(0).getDescricao();
//        a1.setText(questoes.get(0).getAlternativas().get(0).getDescricao());
//        a2.setText(questoes.get(0).getAlternativas().get(1).getDescricao());
//        a3.setText(questoes.get(0).getAlternativas().get(2).getDescricao());
//        a4.setText(questoes.get(0).getAlternativas().get(3).getDescricao());
//        }
        //QuestaoApi questaoApi;
//        i.getExtras();
//        if(questoes != null ){
//            finish();
//
//        }
    }
