package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConclusaoActivity extends AppCompatActivity {
    private Button botaoVoltar;
    private TextView conclusaoText;
    int questoes, acertos, pontos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusao);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        botaoVoltar = findViewById(R.id.voltar);
        conclusaoText = findViewById(R.id.conclusao_text);

        Intent i = getIntent();
        questoes = i.getIntExtra("questões", 0);
        acertos = i.getIntExtra("acertos", 0);
        pontos = i.getIntExtra("pontos", 0);

        conclusaoText.setText("Você concluiu o simulado com " + acertos + " acertos de " + questoes + " questões e ganhou " + pontos + " pontos");
        botaoVoltar.setText("Concluir Simulado \n (+ " + pontos + " pontos)");

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);

        Integer usuarioId = sharedPreferences.getInt("usuarioId", 0);
        Usuario usuario = new Usuario();

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setPontos(pontos);
                usuarioApi.updateUsuarioPontos(usuarioId, usuario).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Pontos adicionados com sucesso", Toast.LENGTH_SHORT).show();
                            editor.putInt("usuarioPontos", response.body().getPontos());
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao adicionar os pontos, tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Erro de conexão, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}