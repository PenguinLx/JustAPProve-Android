package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.ProvaAnterior;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.models.Simulado;
import br.ifsul.justapprove.retrofit.ProvaAnteriorApi;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimuladosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Spinner tempoSpinner, questoesSpinner;
    private Button botaoIniciar;
    private TextView usuarioPontos;
    int numero;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulados);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        usuarioPontos = findViewById(R.id.textview_pontos);
        botaoIniciar = findViewById(R.id.botao_iniciar);
        gridView = findViewById(R.id.gridV_id);
        carregarProvas();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                byte[] pdfBytes = java.util.Base64.getDecoder().decode(v.getTag().toString());
                try {

                    File pdfFile = new File(getExternalFilesDir(null), "prova.pdf");
                    FileOutputStream fos = new FileOutputStream(pdfFile);
                    fos.write(pdfBytes);
                    fos.close();

                    Intent i = new Intent(getApplicationContext(), PdfActivity.class);
                    i.putExtra("pdfFilePath", pdfFile.getAbsolutePath());
                    startActivity(i);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        setTitle("");
        setupToolbar();
        setupDrawer();

        setupSpinners();

        botaoIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfNumber()) {
                    numero = Integer.parseInt(questoesSpinner.getSelectedItem().toString().substring(0, 2));
                } else {
                    numero = Integer.parseInt(questoesSpinner.getSelectedItem().toString().substring(0, 1));
                }

                Intent i = new Intent(getApplicationContext(), JogandoActivity.class);

                i.putExtra("numero", numero);

                startActivity(i);
                finish();

            }
        });
        usuarioPontos.setText(sharedPreferences.getInt("UsuarioPontos", 0) + " pontos");
        setupNavHeader(sharedPreferences.getString("UsuarioApelido", "Estudante"), sharedPreferences.getString("UsuarioImage","Perfil"));
    }

    private void setupNavHeader(String texto, String imagem) {

        View headerView = navigationView.getHeaderView(0);

        TextView headerTextView = headerView.findViewById(R.id.perfil_text);
        ImageView headerImageView = headerView.findViewById(R.id.imageView_foto);

        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PerfilActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);
                i.putExtra("UsuarioId", sharedPreferences.getInt("UsuarioId",0));
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

    private void setupSpinners() {
//        tempoSpinner = findViewById(R.id.tempo_spinner);
        questoesSpinner = findViewById(R.id.questao_spinner);
//        ArrayAdapter<CharSequence> tempoAdapter = ArrayAdapter.createFromResource(this, R.array.tempo_de_resposta, R.layout.spinner_layout);
        ArrayAdapter<CharSequence> questaoAdapter = ArrayAdapter.createFromResource(this, R.array.numero_de_questoes, R.layout.spinner_layout);
//        tempoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        tempoSpinner.setAdapter(tempoAdapter);
        questoesSpinner.setAdapter(questaoAdapter);
    }

    public boolean checkIfNumber() {

        try {
            Log.e("Numero", questoesSpinner.getSelectedItem().toString().substring(1, 2));
            numero = Integer.parseInt(questoesSpinner.getSelectedItem().toString().substring(1, 2));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
        setDefaultMenuItem();
    }

    private void setDefaultMenuItem() {
        MenuItem menuItem = navigationView.getMenu().getItem(1);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        getTitle(menuItem);
        if (menuItem.getItemId() == R.id.opcoes) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getTitle(@NonNull MenuItem menuItem) {
        if (menuItem == navigationView.getMenu().getItem(1)) {

        } else if (menuItem.getItemId() == R.id.home) {
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
            i.putExtra("ultimaActivity", SimuladosActivity.class);
            startActivity(i);
            finish();
        } else if (menuItem.getItemId() == R.id.materia) {
            Intent i = new Intent(getApplicationContext(), MateriaActivity.class);
            startActivity(i);
            finish();
        }
        else if (menuItem.getItemId() == R.id.sair) {
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

    public void carregarProvas() {
        RetrofitService retrofitService = new RetrofitService();
        ProvaAnteriorApi provaAnteriorApi = retrofitService.getRfs().create(ProvaAnteriorApi.class);

        provaAnteriorApi.getProvas().enqueue(new Callback<List<ProvaAnterior>>() {
            @Override
            public void onResponse(Call<List<ProvaAnterior>> call, Response<List<ProvaAnterior>> response) {
                if (response.isSuccessful()) {
                    carregarListaProvas(response.body());
                } else {
                    Toast.makeText(SimuladosActivity.this, "Erro com o carregamento da lista", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProvaAnterior>> call, Throwable throwable) {
                Toast.makeText(SimuladosActivity.this, "Erro com o carregamento da lista", Toast.LENGTH_SHORT).show();
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
            }
        });
    }

    public void carregarListaProvas(List<ProvaAnterior> provas) {
        SimuladoAdapter simuladoAdapter = new SimuladoAdapter(this, provas);

        gridView.setAdapter(simuladoAdapter);
    }


}
