package ccv.checkhelzio.filacademica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FechaFragment extends Fragment implements ActividadesAdapter.ListItemClickListener {
    private RecyclerView recyclerView;
    private Query queryIncidentes;
    private ActividadesAdapter adapter;
    public static final String ARG_PAGE = "ARG_PAGE";
    private final String tabTitles[] = new String[]{"dias.25", "dias.26", "dias.27", "dias.28", "dias.29", "dias.30", "dias.1", "dias.2", "dias.3"};
    private int numero_fragment;


    public static FechaFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FechaFragment fragment = new FechaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numero_fragment = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.fecha_fragment_layout, container, false);
        recyclerView = rootView.findViewById(R.id.recycle);
        iniciarRecyclerView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void iniciarRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        queryIncidentes = FirebaseFirestore.getInstance().collection("actividades").whereEqualTo(tabTitles[numero_fragment], true).orderBy("prioridad", Query.Direction.DESCENDING);
        //queryIncidentes = FirebaseFirestore.getInstance().collection("actividades");

        // Opciones para el adaptador de FirebaseUI
        FirestoreRecyclerOptions<Actividad> options = new FirestoreRecyclerOptions.Builder<Actividad>()
                .setQuery(queryIncidentes, Actividad.class)
                .build();

        adapter = new ActividadesAdapter(getActivity(), options, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(Actividad actividad, View view) {
        Intent i = new Intent(getActivity(), DetalleActividadActivity.class);
        i.putExtra("ACTIVIDAD", actividad);
        startActivity(i);
    }
}