package ccv.checkhelzio.filacademica;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by check on 09/11/2017.
 */

public class PonentesActivity extends AppCompatActivity implements PonentesAdapter.ListItemClickListener, PonentesAdapter.DataChangeListener {
    private static final int DETALLE_PONENTE_DIALOG = 13;
    private ShimmerRecyclerView recyclerView, recyclerViewAnimation;
    private ImageView img_ponentes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ponentes_activity_layout);
        iniciarObjetos();
        setToolbar();
        Picasso.with(this).load(R.drawable.ponentes_fondo).into(img_ponentes);
    }

    private void iniciarObjetos() {
        recyclerView = findViewById(R.id.recycle);
        recyclerViewAnimation = findViewById(R.id.recycle_animation);
        img_ponentes = findViewById(R.id.img_ponentes);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        iniciarRecyclerView();

    }

    private void iniciarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query queryIncidentes = FirebaseFirestore.getInstance().collection("ponentes").orderBy("nombre", Query.Direction.ASCENDING);

        // Opciones para el adaptador de FirebaseUI
        FirestoreRecyclerOptions<Coordinador> options = new FirestoreRecyclerOptions.Builder<Coordinador>()
                .setQuery(queryIncidentes, Coordinador.class)
                .build();

        PonentesAdapter adapter = new PonentesAdapter(this, options, this, this);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onListItemClick(Coordinador coordinador, View view) {
        Intent intent = new Intent(this, DetallePonente.class);
        intent.putExtra("PONENTE", coordinador);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivityForResult(intent, DETALLE_PONENTE_DIALOG, bundle);
    }

    @Override
    public void onDataChangeClick(int itemCount) {
        if (itemCount > 0){
            recyclerViewAnimation.hideShimmerAdapter();
            recyclerViewAnimation.setVisibility(View.GONE);
        }
    }
}
