package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextSenha;
    private Button botaoEnviar, botaoLogin;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //retrofitservice object
        RetrofitService retrofitService = new RetrofitService();
        //api do usuario
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
        editTextEmail = findViewById(R.id.editText_email);
        editTextSenha = findViewById(R.id.editText_senha);
        botaoEnviar = findViewById(R.id.botao_enviar);
        botaoLogin = findViewById(R.id.botao_login);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enviar informações do cadastro por esse botão
                Usuario usr = new Usuario();
                String email = editTextEmail.getText().toString();
                usr.setEmail(email);
                String senha = editTextSenha.getText().toString();
                usr.setSenha(senha);
//                String senha = usr.getSenha();
                int randomNumber = random.nextInt(8999) + 1000;
                usr.setApelido("Estudante" + randomNumber);
                if (!email.isBlank() && !senha.isBlank()) {
                    usuarioApi.saveUsuario(usr).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Save concluido com sucesso!!!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                                startActivity(i);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro com o save!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro com o save!!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Email ou senha vazio, tente novamente!", Toast.LENGTH_LONG).show();
                }
            }
        });
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}