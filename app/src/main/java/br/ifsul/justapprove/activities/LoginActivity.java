package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextSenha;
    private Button botaoEnviar, botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        RetrofitService retrofitService = new RetrofitService();
        //api do usuario
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);

        editTextEmail = findViewById(R.id.editText_email);
        editTextSenha = findViewById(R.id.editText_senha);
        botaoEnviar = findViewById(R.id.botao_enviar);
        botaoVoltar = findViewById(R.id.botao_voltar);

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String senha = editTextSenha.getText().toString();
                if (!email.isBlank() && !senha.isBlank()) {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email);
                    loginRequest.setSenha(senha);
                    Call<LoginResponse> loginResponseCall = usuarioApi.loginUsuario(loginRequest);
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse.getResposta()) {
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    editor.putInt("usuarioId", loginResponse.getId());
                                    editor.putString("usuarioApelido", loginResponse.getApelido());
                                    editor.apply();
                                    Toast.makeText(getApplicationContext(), "Bem vindo " + loginResponse.getApelido(), Toast.LENGTH_SHORT).show();
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Conta não existe", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro com o login", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
//                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                            startActivity(i);
//                            finish();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Email ou senha vazio, tente novamente!", Toast.LENGTH_LONG).show();
                }
            }
        });
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}