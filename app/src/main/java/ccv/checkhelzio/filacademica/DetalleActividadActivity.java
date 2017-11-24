package ccv.checkhelzio.filacademica;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalleActividadActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AppBarLayout appbarLayout;
    private NestedScrollView nestedScrollView;
    private boolean unaVez = false;
    private Actividad actividad;
    private TextView tv_titulo;
    private LinearLayout layout_sesiones;
    private LinearLayout layout_coorinadores;
    private ArrayList<Sesiones> lista_sesiones = new ArrayList<>();
    private ArrayList<Sede> lista_sedes = new ArrayList<>();
    private ArrayList<Coordinador> lista_coordinadores = new ArrayList<>();
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String TAG = DetalleActividadActivity.class.getSimpleName();
    private MapFragment mMapFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_actividad_layout);

        iniciarObjetos();
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void iniciarObjetos() {
        appbarLayout = findViewById(R.id.app_bar);
        nestedScrollView = findViewById(R.id.nested);
        tv_titulo = findViewById(R.id.tv_titulo);
        layout_sesiones = findViewById(R.id.layout_sesiones);
        layout_coorinadores = findViewById(R.id.ly_coordinadores);

        mMapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mMapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) nestedScrollView.getLayoutParams();
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                params.topMargin = verticalOffset / 10;
                nestedScrollView.setLayoutParams(params);
            }
        });

        ponerDatos();
        Picasso.with(DetalleActividadActivity.this).load(actividad.getCartel()).into((ImageView) findViewById(R.id.blur_imageView));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams params2 = (CoordinatorLayout.LayoutParams) appbarLayout.getLayoutParams();
                final AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params2.getBehavior();
                if (behavior != null && !unaVez) {
                    Log.d("NEPE", "onCreate: scroll");
                    ValueAnimator valueAnimator = ValueAnimator.ofInt();
                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            unaVez = true;
                            behavior.setTopAndBottomOffset((Integer) animation.getAnimatedValue());
                            appbarLayout.requestLayout();
                        }
                    });
                    valueAnimator.setIntValues(0, (appbarLayout.getHeight() / 2) * -1);
                    valueAnimator.setDuration(400);
                    valueAnimator.start();
                } else {
                    Log.d("NEPE", "onCreate: null");
                }
            }
        }, 500);
    }

    private void ponerDatos() {
        Log.d(TAG, "ponerDatos:");
        actividad = getIntent().getParcelableExtra("ACTIVIDAD");
        tv_titulo.setText(actividad.getTitulo());
        lista_sesiones = actividad.getSesiones();
        lista_sedes = actividad.getSedes();
        lista_coordinadores = actividad.getCoordinador();

        if (layout_sesiones.getChildCount() < actividad.getSesiones().size()) {
            for (Sesiones sesion : lista_sesiones) {
                View child = getLayoutInflater().inflate(R.layout.fechas_item, layout_sesiones, false);
                ((TextView) child.findViewById(R.id.tv_fecha)).setText(sesion.getFecha());
                ((TextView) child.findViewById(R.id.tv_hora)).setText(sesion.getHora());
                ((TextView) child.findViewById(R.id.tv_lugar)).setText(sesion.getLugar());
                layout_sesiones.addView(child);
            }
        }

        if (layout_coorinadores.getChildCount() < actividad.getCoordinador().size()) {
            for (Coordinador coordinador : lista_coordinadores) {
                View child = getLayoutInflater().inflate(R.layout.ponente_item, layout_coorinadores, false);
                String nombre = coordinador.getNombre().trim() + " " + coordinador.getApellido().trim();
                ((TextView) child.findViewById(R.id.tv_nombre)).setText(nombre);
                if (!TextUtils.isEmpty(coordinador.getImagen())){
                    Picasso.with(this).load(coordinador.getImagen()).into((ImageView) child.findViewById(R.id.iv_coordinador));
                }
                layout_coorinadores.addView(child);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (lista_sedes.size() == 0){
            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(20.6529143, -103.391764))
                    .title("Expo Guadalajara");
            Marker m = googleMap.addMarker(marker);
            m.showInfoWindow();
        }else {
            for (Sede sede : lista_sedes){
                MarkerOptions marker = new MarkerOptions()
                        .position(sede.getLugar_lat_lng())
                        .title(sede.getNombre())
                        .snippet(sede.getDireccion());
                Marker m = googleMap.addMarker(marker);
                m.showInfoWindow();
            }

            if (lista_sedes.size() > 1){
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        lista_sedes.get(0).getLugar_lat_lng(), 11));
            }else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        lista_sedes.get(0).getLugar_lat_lng(), 14));
            }
        }
    }
}
