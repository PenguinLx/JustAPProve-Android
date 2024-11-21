package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcluirContaActivity extends AppCompatActivity {
    private Button cancelar, excluir;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_conta);

        cancelar = findViewById(R.id.botao_cancelar);
        excluir = findViewById(R.id.botao_excluir);
        senha = findViewById(R.id.editText_senha);
        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senhasp = sharedPreferences.getString("UsuarioSenha","") ;
            if(!senha.getText().toString().isBlank()) {
                if(senha.getText().toString().equals(senhasp)){
                    usuarioApi.deleteUsuario(sharedPreferences.getInt("UsuarioId",0)).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Toast.makeText(ExcluirContaActivity.this, "Conta excluida com sucesso!", Toast.LENGTH_SHORT).show();
                            editor.putBoolean("isLogged", false);
                            editor.apply();
                           Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable throwable) {
                            Toast.makeText(ExcluirContaActivity.this, "Falha ao exluir a conta!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    Toast.makeText(ExcluirContaActivity.this, "Senha Incorreta!!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ExcluirContaActivity.this, "Senha Vazia!!!!", Toast.LENGTH_SHORT).show();
            }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}