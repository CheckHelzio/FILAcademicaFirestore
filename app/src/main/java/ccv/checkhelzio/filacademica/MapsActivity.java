package ccv.checkhelzio.filacademica;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    public static ArrayList<Marker> listaMarcadores = new ArrayList<>();
    public static List<Sede> listaSedes = new ArrayList<>();
    private SupportMapFragment mapFragment;
    private ViewPager pagerSedes;
    private InkPageIndicator indicadorSedes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        iniciarObjetos();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setToolbar();

    }

    private void iniciarObjetos() {
        pagerSedes = findViewById(R.id.pagerSedes);
        indicadorSedes = findViewById(R.id.indicador_sedes);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        FirebaseFirestore.getInstance().collection("sedes").orderBy("prioridad", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                listaSedes = documentSnapshots.toObjects(Sede.class);
                iniciarPager();
                for (Sede sede : listaSedes) {
                    LatLng sede_location = new LatLng(sede.getLugar().getLatitude(), sede.getLugar().getLongitude());
                    listaMarcadores.add(mMap.addMarker(new MarkerOptions().position(sede_location).title(sede.getNombre()).snippet(sede.getDireccion())));
                }

                LatLng sede_location = new LatLng(listaSedes.get(0).getLugar().getLatitude(), listaSedes.get(0).getLugar().getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sede_location, 15));
            }
        });

        mMap = googleMap;
        mMap.setPadding(DP(10), DP(48), DP(8f), DP(136));
        mMap.setOnMarkerClickListener(this);

        LatLng sydney = new LatLng(20.6529143, -103.391764);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));


        View toolbar = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).
                getParent()).findViewById(Integer.parseInt("4"));

        // and next place it, for example, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 16, DP(4));
    }

    private void iniciarPager() {
        pagerSedes.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        pagerSedes.setAdapter(new SedesAdapter(getSupportFragmentManager()));
        indicadorSedes.setViewPager(pagerSedes);
        pagerSedes.addOnPageChangeListener(this);
    }

    private int DP(float v) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, getResources().getDisplayMetrics());
    }

    private int DP(int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listaMarcadores.get(position).getPosition(), 15));
        listaMarcadores.get(position).showInfoWindow();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int x = 0;
        for (Marker m : listaMarcadores) {
            if (marker.equals(m)) {
                pagerSedes.setCurrentItem(x, true);
            }
            x++;
        }
        return false;
    }
}
