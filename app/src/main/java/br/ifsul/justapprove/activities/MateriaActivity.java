package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.retrofit.MateriaApi;
import br.ifsul.justapprove.retrofit.RetrofitService;

public class MateriaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private LinearLayout port, mat, geo, his;
    private TextView usuarioPontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        setupToolbar();
        setupDrawer();
        setTitle("");

        usuarioPontos = findViewById(R.id.textview_pontos);
        port = findViewById(R.id.portugues);
        mat = findViewById(R.id.matematica);
        geo = findViewById(R.id.geografia);
        his = findViewById(R.id.historia);
        port.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaMaterialActivity.class);
                RetrofitService rfs = new RetrofitService();
                MateriaApi materiaApi = rfs.getRfs().create(MateriaApi.class);

                i.putExtra("ultimaActivity", MateriaActivity.class);
                startActivity(i);
                finish();
            }
        });
        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaMaterialActivity.class);
                RetrofitService rfs = new RetrofitService();
                MateriaApi materiaApi = rfs.getRfs().create(MateriaApi.class);
                i.putExtra("ultimaActivity", MateriaActivity.class);
                startActivity(i);
                finish();
            }
        });
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaMaterialActivity.class);
                RetrofitService rfs = new RetrofitService();
                MateriaApi materiaApi = rfs.getRfs().create(MateriaApi.class);
                i.putExtra("ultimaActivity", MateriaActivity.class);
                startActivity(i);
                finish();
            }
        });
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaMaterialActivity.class);
                RetrofitService rfs = new RetrofitService();
                MateriaApi materiaApi = rfs.getRfs().create(MateriaApi.class);
                i.putExtra("ultimaActivity", MateriaActivity.class);
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
