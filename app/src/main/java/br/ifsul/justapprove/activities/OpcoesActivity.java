package br.ifsul.justapprove.activities;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpcoesActivity extends AppCompatActivity {
    private Button cancelar, enviar, imagem;
    private EditText editTextSenha, editTextApelido;
    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int REQUEST_PERMISSION_CODE = 200;
    private Uri selectedImageUri;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }

        cancelar = findViewById(R.id.botao_cancelar);
        enviar = findViewById(R.id.botao_enviar);
        imagem = findViewById(R.id.botao_imagem);
        imageView = findViewById(R.id.imageView_foto);
        editTextSenha = findViewById(R.id.editText_senha);
        editTextApelido = findViewById(R.id.editText_apelido);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);

        Integer usuarioId = sharedPreferences.getInt("UsuarioId", 0);
        Usuario usuario = new Usuario();

        setDefaultImage();

        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextSenha.getText().toString().isBlank() || !editTextApelido.getText().toString().isBlank() || selectedImageUri != null) {
                    usuario.setSenha(editTextSenha.getText().toString());
                    usuario.setApelido(editTextApelido.getText().toString());

                    if (selectedImageUri != null) {
                        byte[] imageBytes;
                        try {
                            imageBytes = getBytesFromFile(selectedImageUri);
                            Bitmap bMap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            imageView.setImageBitmap(bMap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        usuario.setImage(Base64.getEncoder().encodeToString(imageBytes));
                        Bitmap bMap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        imageView.setImageBitmap(bMap);
                    }

                    usuarioApi.updateUsuario(usuarioId, usuario).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getApelido().equals("Apelido já em uso")) {
                                    Toast.makeText(OpcoesActivity.this, response.body().getApelido(), Toast.LENGTH_SHORT).show();
                                } else if(response.body().getSenha().equals("Senha já em uso")){
                                    Toast.makeText(OpcoesActivity.this, response.body().getSenha(), Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    Toast.makeText(OpcoesActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                                    editor.putString("UsuarioApelido", response.body().getApelido());
                                    editor.putString("UsuarioImage", response.body().getImage());
                                    editor.apply();
                                }
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
                } else {
                    Toast.makeText(OpcoesActivity.this, "Nenhum dado preenchido", Toast.LENGTH_SHORT).show();
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

    public void setDefaultImage() {
        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        byte[] imagemB = java.util.Base64.getDecoder().decode(sharedPreferences.getString("UsuarioImage","Perfil"));
        Bitmap bMap = BitmapFactory.decodeByteArray(imagemB, 0, imagemB.length);
        imageView.setImageBitmap(bMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytesFromFile(Uri imageUri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }
}