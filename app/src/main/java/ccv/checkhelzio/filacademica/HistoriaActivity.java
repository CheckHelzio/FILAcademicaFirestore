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



public class HistoriaActivity extends AppCompatActivity implements HistoriaAdapter.DataChangeListener {
    private ShimmerRecyclerView recyclerView, recyclerViewAnimation;
    private ImageView img_historias;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historia_activity_layout);
        iniciarObjetos();
        setToolbar();
        Picasso.with(this).load(R.drawable.img_historia).into(img_historias);
    }

    private void iniciarObjetos() {
        recyclerView = findViewById(R.id.recycle);
        recyclerViewAnimation = findViewById(R.id.recycle_animation);
        img_historias = findViewById(R.id.img_historia);
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

        Query queryIncidentes = FirebaseFirestore.getInstance().collection("historia").orderBy("prioridad", Query.Direction.ASCENDING);

        // Opciones para el adaptador de FirebaseUI
        FirestoreRecyclerOptions<Historia> options = new FirestoreRecyclerOptions.Builder<Historia>()
                .setQuery(queryIncidentes, Historia.class)
                .build();

        HistoriaAdapter adapter = new HistoriaAdapter(this, options, this);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onDataChangeClick(int itemCount) {
        if (itemCount > 0){
            recyclerViewAnimation.hideShimmerAdapter();
            recyclerViewAnimation.setVisibility(View.GONE);
        }
    }
}
