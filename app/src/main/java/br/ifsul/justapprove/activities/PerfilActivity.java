package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.ProvaAnterior;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.ProvaAnteriorApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView apelido;
    private ImageView fotoPerfil;
    private TextView usuarioPontos, perfilPontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        apelido = findViewById(R.id.perfil_apelido);
        usuarioPontos = findViewById(R.id.textview_pontos);
        fotoPerfil = findViewById(R.id.perfil_foto);
        perfilPontos = findViewById(R.id.perfil_pontos);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        setTitle("");
        setupToolbar();
        setupDrawer();
        setupNavHeader(sharedPreferences.getString("UsuarioApelido", "Estudante"), sharedPreferences.getString("UsuarioImage","Perfil"));

        usuarioPontos.setText(sharedPreferences.getInt("UsuarioPontos", 0) + " pontos");


        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);

        Integer usuarioId = getIntent().getIntExtra("UsuarioId", 0);

        if (usuarioId == 0) {
            String imagem = sharedPreferences.getString("UsuarioImage","Perfil");
            if(imagem.equals("Perfil")) {
                fotoPerfil.setImageResource(R.drawable.perfil);
            }
            else {
                byte[] imagemB = java.util.Base64.getDecoder().decode(imagem);
                Bitmap bMap = BitmapFactory.decodeByteArray(imagemB, 0, imagemB.length);
                fotoPerfil.setImageBitmap(bMap);
            }
            apelido.setText(sharedPreferences.getString("UsuarioApelido", "Estudante"));
            perfilPontos.setText(sharedPreferences.getInt("UsuarioPontos", 0) + " Pontos");
        }
        else {
            usuarioApi.readUsuarioById(usuarioId).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getImage() == null) {
                            fotoPerfil.setImageResource(R.drawable.perfil);
                        } else {
                            byte[] imagem = java.util.Base64.getDecoder().decode(response.body().getImage());
                            Bitmap bMap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                            fotoPerfil.setImageBitmap(bMap);
                        }


                        apelido.setText(response.body().getApelido());
                        perfilPontos.setText(response.body().getPontos() + " Pontos");
                    } else {
                        Toast.makeText(PerfilActivity.this, "Triste", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable throwable) {
                    Toast.makeText(PerfilActivity.this, "Ocorreu um erro com o carregamento!!!", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(PerfilActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
                }
            });
        }
    }
    private void setupNavHeader(String texto, String imagem) {

        View headerView = navigationView.getHeaderView(0);

        TextView headerTextView = headerView.findViewById(R.id.perfil_text);
        ImageView headerImageView = headerView.findViewById(R.id.imageView_foto);

        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(i);
                finish();
            }
        });

        headerTextView.setText(texto);
        if(imagem.equals("Perfil")) {
            headerImageView.setImageResource(R.drawable.perfil);
        }
        else {
            byte[] imagemB = java.util.Base64.getDecoder().decode(imagem);
            Bitmap bMap = BitmapFactory.decodeByteArray(imagemB, 0, imagemB.length);
            headerImageView.setImageBitmap(bMap);
        }
    }


    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(this);

        setupNavigationView();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
//        setDefaultMenuItem();
    }

//    private void setDefaultMenuItem() {
//        MenuItem menuItem = navigationView.getMenu().getItem(1);
//        onNavigationItemSelected(menuItem);
//        menuItem.setChecked(true);
//    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        getTitle(menuItem);
        if (menuItem.getItemId() == R.id.opcoes) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getTitle(MenuItem menuItem) {
         if (menuItem.getItemId() == R.id.home) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        } else if (menuItem.getItemId() == R.id.simulados) {
            Intent i = new Intent(getApplicationContext(), SimuladosActivity.class);
            startActivity(i);
            finish();
        } else if (menuItem.getItemId() == R.id.opcoes) {
            Intent i = new Intent(getApplicationContext(), OpcoesActivity.class);
            startActivity(i);
        } else if (menuItem.getItemId() == R.id.ranking) {
            Intent i = new Intent(getApplicationContext(), RankingActivity.class);
            i.putExtra("ultimaActivity", HomeActivity.class);
            startActivity(i);
            finish();
        } else if (menuItem.getItemId() == R.id.materia) {
            Intent i = new Intent(getApplicationContext(), MateriaActivity.class);
            startActivity(i);
            finish();
        } else if (menuItem.getItemId() == R.id.sair) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogged", false);
            editor.apply();
            startActivity(i);
            finish();
        }
        else {
            throw new IllegalArgumentException("menu option not implemented!!");
        }
    }

    private void showFragment(@StringRes int titleId) {
        setTitle("");
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
//    Toast.makeText(this, getString(R.string.navigation_drawer_open),
//            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }

}