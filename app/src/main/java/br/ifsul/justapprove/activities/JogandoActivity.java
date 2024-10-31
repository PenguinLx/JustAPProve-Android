package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogandoActivity extends AppCompatActivity {
    private TextView questaoText;
    private ImageView questaoImage;
    private Button a1, a2, a3, a4, proximaQuestao;
    private boolean respondendo;
    private int numero, questaoAtual, acertos, pontos;
    private String resposta;
    private Drawable defaultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogando);

        questaoText = findViewById(R.id.questao_text);
        questaoImage = findViewById(R.id.questao_image);
        proximaQuestao = findViewById(R.id.proxima_questao);

        a1 = findViewById(R.id.resposta1);
        a2 = findViewById(R.id.resposta2);
        a3 = findViewById(R.id.resposta3);
        a4 = findViewById(R.id.resposta4);
        Button[] alternativas = {a1, a2, a3, a4};

        defaultButton = proximaQuestao.getBackground();

        for (Button alternativa : alternativas) {
            alternativa.setEnabled(false);
            alternativa.setBackgroundColor(R.drawable.botao_style_enabled);
        }

        questaoAtual = 0;
        acertos = 0;
        respondendo = true;

        RetrofitService rfs = new RetrofitService();
        QuestaoApi questaoApi = rfs.getRfs().create(QuestaoApi.class);

        Intent i = getIntent();
        numero = i.getIntExtra("numero", 0);
        //Request questoes = questaoApi.getAllQuestao().request();
        int nQuestoes = 0;
        try {
            nQuestoes = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
//        try {
//           nQuestoes = questaoApi.getAllQuestao().execute().body().size();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        if(nQuestoes < numero){
            questaoApi.gerarSimulado(nQuestoes).enqueue(new Callback<List<Questao>>() {
                @Override
                public void onResponse(Call<List<Questao>> call, Response<List<Questao>> response) {
                    List<Questao> questList = response.body();

                    displayQuestao(questaoAtual, alternativas, questList);
                    for (Button alternativa : alternativas) {
                        alternativa.setEnabled(true);
                    }
                }

                @Override
                public void onFailure(Call<List<Questao>> call, Throwable throwable) {
                    Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
                }
            });
        }
        else questaoApi.gerarSimulado(numero).enqueue(new Callback<List<Questao>>() {
            @Override
            public void onResponse(Call<List<Questao>> call, Response<List<Questao>> response) {

                List<Questao> questList = response.body();

                displayQuestao(questaoAtual, alternativas, questList);
                for (Button alternativa : alternativas) {
                    alternativa.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<Questao>> call, Throwable throwable) {
                Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
            }
        });
    }

    public void displayQuestao(int questaoAtual, Button[] alternativas, List<Questao> questList) {
        questaoText.setText("Questão " + (questaoAtual + 1) + " de " + questList.size());
        byte[] imagem = java.util.Base64.getDecoder().decode(questList.get(questaoAtual).getDescricao());
        Bitmap bMap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        questaoImage.setImageBitmap(bMap);
        for (int x = 0; x < questList.get(questaoAtual).getAlternativas().size(); x++) {
            alternativas[x].setText(questList.get(questaoAtual).getAlternativas().get(x).getDescricao());
            if (questList.get(questaoAtual).getAlternativas().get(x).isCorreta()) {
                alternativas[x].setTag("correta");
            } else {
                alternativas[x].setTag("incorreta");
            }
        }

        for (int x = 0; x < alternativas.length; x++) {
            alternativas[x].setOnClickListener(v -> {
                resposta = v.getTag().toString();
                proximaQuestao.setText("Confirmar Resposta");
                proximaQuestao.setVisibility(View.VISIBLE);
                proximaQuestao.setOnClickListener(y -> {
                    if (respondendo) {
                        for (Button alternativa : alternativas) {
                            alternativa.setEnabled(false);
                            if (alternativa.getTag().equals("correta")) {
                                alternativa.setBackgroundColor(getResources().getColor(R.color.verde_claro, null));
                            }
                        }
                        if (resposta.equals("correta")) {
                            Log.e("Certo", questList.get(questaoAtual).getDescricao());
                            acertos++;
                            pontos += 10;
                        } else {
                            v.setBackgroundColor(getResources().getColor(R.color.vermelho, null));
                            Log.e("Errado", questList.get(questaoAtual).getDescricao());
                        }
                        respondendo = false;
                        if (this.questaoAtual == questList.size() - 1) {
                            proximaQuestao.setText("Finalizar Simulado");
                        } else {
                            proximaQuestao.setText("Proxima Questão");
                        }

                    } else {
                        for (Button alternativa : alternativas) {
                            alternativa.setEnabled(true);
                            alternativa.setBackgroundColor(R.drawable.botao_style_enabled);
                        }
                        respondendo = true;
                        proximaQuestao.setVisibility(View.INVISIBLE);
                        this.questaoAtual++;
                        if (this.questaoAtual < questList.size()) {
                            displayQuestao(this.questaoAtual, alternativas, questList);
                        } else {
                            Intent i = new Intent(getApplicationContext(), ConclusaoActivity.class);
                            i.putExtra("pontos", pontos);
                            i.putExtra("acertos", acertos);
                            i.putExtra("questões", numero);
                            startActivity(i);
                            finish();
                        }
                    }
                });

            });
        }
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    Future<Integer> future = executor.submit(() -> {
        RetrofitService rfs = new RetrofitService();
        QuestaoApi questaoApi = rfs.getRfs().create(QuestaoApi.class);
        try {
            // Retorna o tamanho da lista de questões da API
            return questaoApi.getAllQuestao().execute().body().size();

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retorne 0 ou outro valor que indique erro ou lista vazia
        }
    });
}
