package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Strictness;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.retrofit.LoginRequest;
import br.ifsul.justapprove.retrofit.LoginResponse;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EsqueceuSenhaActivity extends AppCompatActivity {
    private EditText email, codigo;
    private String token;
    private Button enviar, voltar;
   private boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        email = findViewById(R.id.editText_email);
        codigo = findViewById(R.id.editText_codigo);
        enviar = findViewById(R.id.botao_enviar);
        voltar = findViewById(R.id.botao_voltar);

        //erro?JsonReader.setStrictness(Strictness.LENIENT);
        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService = new RetrofitService();
                //api do usuario
                UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
                    if(!login) {
                        if (!email.getText().toString().isBlank()) {
                            usuarioApi.forgotPass(email.getText().toString()).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().contains("inválido") || response.body().contains("aguarde")) {
                                            Toast.makeText(EsqueceuSenhaActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            token = response.body();
                                            enviar.setText("Enviar");
                                            enviar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                            email.setEnabled(false);
                                            codigo.setEnabled(true);
                                            codigo.setBackgroundResource(R.drawable.borda);
                                            email.setBackgroundResource(R.drawable.borda_fundo_claro);
                                            login = true;
                                        }
                                    }
                                }


                                @Override
                                public void onFailure(Call<String> call, Throwable throwable) {
                                    Toast.makeText(EsqueceuSenhaActivity.this, "Erro, Email Incorreto", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(EsqueceuSenhaActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
                                }
                            });
                        } else {
                            Toast.makeText(EsqueceuSenhaActivity.this, "Email em branco!", Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setToken(token);
                        loginRequest.setEmail(email.getText().toString());
                        if (!codigo.getText().toString().isBlank()) {
                            usuarioApi.loginCod(loginRequest).enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    if (response.isSuccessful()) {
                                        LoginResponse loginResponse = response.body();
                                        if (loginResponse.getResposta()) {
                                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                            editor.putInt("UsuarioId", loginResponse.getId());
                                            editor.putString("UsuarioApelido", loginResponse.getApelido());
                                            editor.putInt("UsuarioPontos", loginResponse.getPontos());
                                            if (loginResponse.getImage() == null) {
                                                editor.putString("UsuarioImage", "Perfil");
                                            } else {
                                                editor.putString("UsuarioImage", loginResponse.getImage());
                                            }

                                            editor.putBoolean("isLogged", true);
                                            editor.apply();
                                            Toast.makeText(getApplicationContext(), "Bem vindo " + loginResponse.getApelido(), Toast.LENGTH_SHORT).show();
                                            startActivity(i);
                                            finish();

                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                                    Toast.makeText(EsqueceuSenhaActivity.this, "Erro com o login!", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(EsqueceuSenhaActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
                                }
                            });

                        } else {
                            Toast.makeText(EsqueceuSenhaActivity.this, "Código em branco!", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });


    }
}