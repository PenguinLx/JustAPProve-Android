package br.ifsul.justapprove.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Materia;
import br.ifsul.justapprove.models.Material;
import br.ifsul.justapprove.models.Usuario;
import br.ifsul.justapprove.retrofit.MateriaApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
import br.ifsul.justapprove.retrofit.UsuarioApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaMaterialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener, OnClickListener {

    private MateriaAdapter materiaAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private RecyclerView lista;
    private Button botaoVoltar;
    private TextView usuarioPontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_material);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        setTitle("");
        setupToolbar();
        setupDrawer();

        usuarioPontos = findViewById(R.id.textview_pontos);
        botaoVoltar = findViewById(R.id.botao_voltar);

        setupLista();
        carregarMateria();

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


    @Override
    public void onClick(int position) {

        //////PEGAR UM FUCKING MATERIAL SÓ/////
//        Intent i = new Intent(getApplicationContext(), MaterialActivity.class);
//        i.putExtra("NomeMateria", materiaAdapter.getItem(position).getNome());
//
//
//        Materia elemento = materiaAdapter.getItem(position);
//        SharedPreferences sharedPreferences = getSharedPreferences("Material", MODE_PRIVATE);
////        List<Materia> listMateriaSP = sharedPreferences.
//        //SharedPreferences.Editor editor = sharedPreferences.edit();
//        int tamnhoMaterial = elemento.getMateriais().size();
//        List<Material> listMat = elemento.getMateriais();
//        i.putExtra("LIST", (Serializable) listMat);
//        Toast.makeText(this, "position " + elemento.getNome(), Toast.LENGTH_SHORT).show();
        RetrofitService rfs = new RetrofitService();
        MateriaApi materiaApi = rfs.getRfs().create(MateriaApi.class);
        Materia elemento = materiaAdapter.getItem(position);
        Intent i = new Intent(getApplicationContext(), MaterialActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("Material", MODE_PRIVATE);
//        List<Materia> listMateriaSP = sharedPreferences.
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //int tamnhoMaterial = elemento.getMateriais().size();
        //List<Material> listMat = elemento.getMateriais();
        //i.putExtra("LIST", (Serializable) listMat);
        i.putExtra("descricaoMaterial", elemento.getMaterial().getDescricao());
        i.putExtra("videoMaterial", elemento.getMaterial().getVideoEmbedd());
        i.putExtra("nomeMateria", elemento.getNome());
        startActivity(i);
        finish();
    }

    private void changeNavHeaderText(String texto) {
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.perfil_text);
        headerTextView.setText(texto);
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
        MenuItem menuItem = navigationView.getMenu().getItem(4);
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
        if (menuItem == navigationView.getMenu().getItem(4)) {

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
            i.putExtra("ultimaActivity", MateriaActivity.class);
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
        } else {
            throw new IllegalArgumentException("menu option not implemented!!");
        }
    }

    public void carregarMateria() {
        RetrofitService retrofitService = new RetrofitService();
        MateriaApi materiaApi = retrofitService.getRfs().create(MateriaApi.class);
        Intent i = getIntent();
        materiaApi.getAllMateriaByTipo(i.getStringExtra("TipoMateria")).enqueue(new Callback<List<Materia>>() {
            @Override
            public void onResponse(Call<List<Materia>> call, Response<List<Materia>> response) {
                if (response.isSuccessful()){
                    carregarLista(response.body());
                    Toast.makeText(ListaMaterialActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Materia>> call, Throwable throwable) {
                Toast.makeText(ListaMaterialActivity.this, "ERRO!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void carregarLista(List<Materia> listaMaterias) {
         materiaAdapter = new MateriaAdapter(listaMaterias, this);
        lista.setAdapter(materiaAdapter);
    }

    public void setupLista(){
        lista = findViewById(R.id.lista_conteudos);
        lista.setLayoutManager(new LinearLayoutManager(this));
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