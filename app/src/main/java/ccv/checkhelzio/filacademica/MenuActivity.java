package ccv.checkhelzio.filacademica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean mTwoPane;
    private LinearLayout ly_ponentes, ly_actividades, ly_sedes, ly_historia;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        iniciarObjetos();
        iniciarToolbar();
    }

    private void iniciarToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void iniciarObjetos() {
        ly_ponentes = findViewById(R.id.ly_ponentes);
        ly_actividades = findViewById(R.id.ly_actividades);
        ly_sedes = findViewById(R.id.ly_sedes);
        ly_historia = findViewById(R.id.ly_historia);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setListeners() {
        ly_ponentes.setOnClickListener(this);
        ly_actividades.setOnClickListener(this);
        ly_sedes.setOnClickListener(this);
        ly_historia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.ly_ponentes:
                i = new Intent(MenuActivity.this, PonentesActivity.class);
                startActivity(i);
                break;
            case R.id.ly_actividades:
                i = new Intent(MenuActivity.this, ActividadesActivity.class);
                startActivity(i);
                break;
            case R.id.ly_historia:
                i = new Intent(MenuActivity.this, HistoriaActivity.class);
                startActivity(i);
                break;
            case R.id.ly_sedes:
                i = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListeners();
    }
}
