package br.ifsul.justapprove.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ifsul.justapprove.R;
import br.ifsul.justapprove.models.Questao;
import br.ifsul.justapprove.models.Simulado;
import br.ifsul.justapprove.retrofit.QuestaoApi;
import br.ifsul.justapprove.retrofit.RetrofitService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulados);

        SharedPreferences sharedPreferences = getSharedPreferences("Dados", MODE_PRIVATE);

        botaoIniciar = findViewById(R.id.botao_iniciar);

        setTitle("");
        setupToolbar();
        setupDrawer();

        setupSpinners();

        botaoIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numero = Integer.parseInt(questoesSpinner.getSelectedItem().toString().substring(0,1));

                Intent i = new Intent(getApplicationContext(), JogandoActivity.class);

                i.putExtra("numero", numero);

                startActivity(i);
                finish();
//                questaoApi.gerarSimulado(numero).enqueue(new Callback<List<Questao>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<Questao>> call, @NonNull Response<List<Questao>> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(SimuladosActivity.this, "Simulado Gerado!", Toast.LENGTH_SHORT).show();
//                            Log.e("Erro", response.body().get(0).getAlternativas().get(0).getDescricao());
//                            Intent i = new Intent(getApplicationContext(), JogandoActivity.class);
//                            Bundle b = new Bundle();
//                            List<Questao> questoes = response.body();
//                            //i.putParcelableArrayListExtra("listQuestao", new ArrayList<>(questoes));
//                            b.putParcelableArrayList("listQuestao", (ArrayList<? extends Parcelable>) questoes);
//                            i.putExtras(b);
//                            startActivity(i);
//                            finish();
//                        } else {
//                            Toast.makeText(SimuladosActivity.this, "Resposta não sucedida!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onFailure(@NonNull Call<List<Questao>> call, @NonNull Throwable throwable) {
//                        Toast.makeText(SimuladosActivity.this, "Erro ao gerar simulado!", Toast.LENGTH_SHORT).show();
//                        Logger.getLogger(SimuladosActivity.class.getName()).log(Level.SEVERE, "Erro!", throwable);
//                    }
//                });


            }
        });
        changeNavHeaderText(sharedPreferences.getString("usuarioApelido", "Estudante"));
    }

    private void changeNavHeaderText(String texto) {
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.perfil_text);
        headerTextView.setText(texto);
    }

    private void setupSpinners() {
        tempoSpinner = findViewById(R.id.tempo_spinner);
        questoesSpinner = findViewById(R.id.questao_spinner);
        ArrayAdapter<CharSequence> tempoAdapter = ArrayAdapter.createFromResource(this, R.array.tempo_de_resposta, R.layout.spinner_layout);
        ArrayAdapter<CharSequence> questaoAdapter = ArrayAdapter.createFromResource(this, R.array.numero_de_questoes, R.layout.spinner_layout);
        tempoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempoSpinner.setAdapter(tempoAdapter);
        questoesSpinner.setAdapter(questaoAdapter);
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
