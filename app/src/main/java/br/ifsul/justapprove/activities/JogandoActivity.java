package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JogandoActivity extends AppCompatActivity {
    private TextView questaoText;
    private PhotoView questaoImage;
    private Button a1, a2, a3, a4, proximaQuestao;
    private boolean respondendo;
    private int numero, questaoAtual, acertos, pontos;
    private String resposta;

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

        questaoApi.gerarSimulado(numero).enqueue(new Callback<List<Questao>>() {
            @Override
            public void onResponse(Call<List<Questao>> call, Response<List<Questao>> response) {
                if (response.body().get(0).getAlternativas().get(0).getDescricao().contains("ERRO:")){
                    Toast.makeText(JogandoActivity.this, "ERRO: NÃO HÁ QUESTÕES SUFICIENTES NO BANCO DE DADOS!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
                    startActivity(i);
                    finish();

                }

                else {
                    List<Questao> questList = response.body();
                    displayQuestao(questaoAtual, alternativas, questList);
                    for (Button alternativa : alternativas) {
                        alternativa.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Questao>> call, Throwable throwable) {
                Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
                startActivity(i);
                finish();
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
                v.setEnabled(false);
                for (Button alternativa : alternativas) {
                    if (alternativa != v) {
                        alternativa.setEnabled(true);
                    }
                }
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
}