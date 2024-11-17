package br.ifsul.justapprove.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private int PICK_IMAGE_REQUEST = 0;
    private File imageFile;
    private Uri selectedImageUri;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        cancelar = findViewById(R.id.botao_cancelar);
        enviar = findViewById(R.id.botao_enviar);
        imagem = findViewById(R.id.botao_imagem);
        imageView = findViewById(R.id.imageView_foto);
        editTextSenha = findViewById(R.id.editText_senha);
        editTextApelido = findViewById(R.id.editText_apelido);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);

        Integer usuarioId = sharedPreferences.getInt("usuarioId", 0);
        Usuario usuario = new Usuario();

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
                usuario.setSenha(editTextSenha.getText().toString());
                usuario.setApelido(editTextApelido.getText().toString());

//                if (imageBytes != null) {
//                    usuario.setImage(Base64.getEncoder().encodeToString(imageBytes));
//                }

                imageFile = new File(getRealPathFromURI(selectedImageUri));

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);

                usuarioApi.updateUsuario(usuarioId,usuario, imagePart).enqueue(new Callback<Usuario>() {
                    @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        if (response.body().getApelido().equals("Apelido já em uso")) {
                            Toast.makeText(OpcoesActivity.this, response.body().getApelido(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OpcoesActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                            editor.putString("UsuarioApelido", response.body().getApelido());
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
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            try {
                // Display the selected image in the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImage(byte[] imagem) {
        Bitmap bMap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        imageView.setImageBitmap(bMap);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }
}