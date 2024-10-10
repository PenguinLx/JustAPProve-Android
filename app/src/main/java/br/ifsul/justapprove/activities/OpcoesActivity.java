package br.ifsul.justapprove.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpcoesActivity extends AppCompatActivity {
    //TODO:resolver a questão do Intent e a questão do campo nulo
    private Button cancelar, enviar;
    private EditText editTextSenha, editTextApelido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);
        cancelar = findViewById(R.id.botao_cancelar);
        enviar = findViewById(R.id.botao_enviar);
        editTextSenha = findViewById(R.id.editText_senha);
        editTextApelido = findViewById(R.id.editText_apelido);

        Intent i = getIntent();
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
        Integer usuarioId = i.getIntExtra("usuarioId",0);
        Usuario usuario = new Usuario();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setSenha(editTextSenha.getText().toString());
                usuario.setApelido(editTextApelido.getText().toString());

                usuarioApi.updateUsuario(usuarioId,usuario).enqueue(new Callback<Usuario>() {
                    @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(OpcoesActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                    //else if se resposta for nula?
                    else {
                        Toast.makeText(OpcoesActivity.this, "A resposta não foi sucedida", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable throwable) {
                        Toast.makeText(OpcoesActivity.this, "Erro de conexão!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(OpcoesActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);

                    }
                });
                finish();
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