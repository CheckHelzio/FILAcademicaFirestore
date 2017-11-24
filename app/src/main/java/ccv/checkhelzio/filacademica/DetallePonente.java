package ccv.checkhelzio.filacademica;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by check on 16/11/2017.
 */

public class DetallePonente extends Activity {
    private Coordinador coordinador;
    private CircleImageView imagen;
    private TextView tv_nombre;
    private LinearLayout layout_parrafos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ponente_detalle);
        postponeEnterTransition();
        iniciarObjetos();
        ponerDatos();

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();
    }

    private void ponerDatos() {
        if (!TextUtils.isEmpty(coordinador.getImagen())){
            Picasso.with(this).load(coordinador.getImagen()).into(imagen);
        }
        String nombre = coordinador.getNombre().trim() + " " + coordinador.getApellido();
        tv_nombre.setText(nombre);

        if (coordinador.getDescripcion() != null){
            for (String parrafo : coordinador.getDescripcion()) {
                View child = getLayoutInflater().inflate(R.layout.parrafo_item, layout_parrafos, false);
                ((DocumentView) child).setText(parrafo);
                layout_parrafos.addView(child);
            }
        }
    }

    private void iniciarObjetos() {
        coordinador = getIntent().getParcelableExtra("PONENTE");
        imagen = findViewById(R.id.iv_coordinador);
        tv_nombre = findViewById(R.id.tv_nombre);
        layout_parrafos = findViewById(R.id.layout_parrafos);
    }

    public void dismiss(View view) {
        finishAfterTransition();
    }
}
