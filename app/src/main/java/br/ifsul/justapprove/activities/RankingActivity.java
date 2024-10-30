package br.ifsul.justapprove.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView lista;
    private Button botaoVoltar;
    private TextView usuarioPontos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        usuarioPontos = findViewById(R.id.textview_pontos);
        botaoVoltar = findViewById(R.id.botao_voltar);

        setTitle("");
        setupToolbar();
        setupDrawer();

        setupLista();
        carregarRanking();

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Class ultimaClasse = (Class<Activity>)extras.getSerializable("ultimaActivity");
                Intent i = new Intent(getApplicationContext(), ultimaClasse);
                startActivity(i);
                finish();
            }
        });
        usuarioPontos.setText(sharedPreferences.getInt("usuarioPontos", 0) + " pontos");
        changeNavHeaderText(sharedPreferences.getString("usuarioApelido", "Estudante"));
    }

    private void changeNavHeaderText(String texto) {
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.perfil_text);
        headerTextView.setText(texto);
    }

    private void setupLista() {
        lista = findViewById(R.id.lista_ranking);
        lista.setLayoutManager(new LinearLayoutManager(this));
//        ArrayList<RankingAdicionar> pesquisarRanking = GetResultadosRankingAdicionar();
//        lista.setAdapter(new RankingAdapter(this, pesquisarRanking));
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
        MenuItem menuItem = navigationView.getMenu().getItem(3);
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
        if (menuItem == navigationView.getMenu().getItem(3)) {

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
        } else if (menuItem.getItemId() == R.id.materia) {
            Intent i = new Intent(getApplicationContext(), MateriaActivity.class);
            startActivity(i);
            finish();
        } else {
            throw new IllegalArgumentException("menu option not implemented!!");
        }
    }

//    private ArrayList<RankingAdicionar> GetResultadosRankingAdicionar(){
//        ArrayList<RankingAdicionar> results = new ArrayList<RankingAdicionar>();
//
//        RankingAdicionar sr1 = new RankingAdicionar();
//        sr1.setApelido("John Smith");
//        sr1.setPontuacao("10000");
//        RankingAdicionar sr2 = new RankingAdicionar();
//        sr2.setApelido("John Smith");
//        sr2.setPontuacao("999999999999");
//        results.add(sr1);
//        results.add(sr2);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr1);
//        results.add(sr2);
//
//        return results;
//    }

    public void carregarRanking() {
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRfs().create(UsuarioApi.class);
        usuarioApi.getAllUsuarioByPontos()
                .enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        if (response.isSuccessful()) {
                            carregarLista(response.body());
                        } else {
                            Toast.makeText(RankingActivity.this, "Ocorreu um erro com o carregamento!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable throwable) {
                        Toast.makeText(RankingActivity.this, "Ocorreu um erro com o carregamento!!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,"Erro!",throwable);
                    }
                });
    }

    public void carregarLista(List<Usuario> listaUsuarios) {
        RankingAdapter rankingAdapter = new RankingAdapter(listaUsuarios);
        lista.setAdapter(rankingAdapter);
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