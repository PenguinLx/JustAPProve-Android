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
    private EditText senhaEdit, userNEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);
        cancelar = findViewById(R.id.botao_cancelar);
        enviar = findViewById(R.id.botao_enviar);
        senhaEdit = findViewById(R.id.editText_senha);
        userNEdit = findViewById(R.id.editText_apelido);

        Intent i = getIntent();
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
        //Integer idU = i.getIntExtra("usuarioId",0);
        Integer idU = 8;
        Usuario usr = new Usuario();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr.setUserName(userNEdit.getText().toString());
                usr.setSenha(senhaEdit.getText().toString());

                usuarioApi.updateUsuario(idU,usr).enqueue(new Callback<Usuario>() {
                    @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(OpcoesActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                    //else if se resposta for nula?
                    else {
                        Toast.makeText(OpcoesActivity.this, "ocorreu algum erro!", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable throwable) {
                        Toast.makeText(OpcoesActivity.this, "Ocorreu um erro ao atualizar os dados!", Toast.LENGTH_SHORT).show();
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