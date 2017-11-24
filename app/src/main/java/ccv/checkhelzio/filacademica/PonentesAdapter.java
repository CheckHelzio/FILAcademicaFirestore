package ccv.checkhelzio.filacademica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

/**
 * Created by check on 09/11/2017.
 */

class PonentesAdapter extends FirestoreRecyclerAdapter<Coordinador, PonentesAdapter.PonentesViewHolder> {


    final private ListItemClickListener mOnClcikListener;
    final private DataChangeListener mOnDataChangeListener;
    private Context context;

    public interface ListItemClickListener {
        void onListItemClick(Coordinador clickedItemIndex, View view);
    }
    public interface DataChangeListener {
        void onDataChangeClick(int itemCount);
    }

    public PonentesAdapter(Context context, FirestoreRecyclerOptions<Coordinador> options, ListItemClickListener listItemClickListener, DataChangeListener onDataChangeListener) {
        super(options);
        this.context = context;
        mOnClcikListener = listItemClickListener;
        mOnDataChangeListener = onDataChangeListener;
    }

    @Override
    public PonentesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ponente_item2;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new PonentesViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(PonentesViewHolder holder, int position, Coordinador model) {
        holder.bind(model);
    }

    class PonentesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagen;
        private TextView tv_nombre;

        PonentesViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.iv_coordinador);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            itemView.setOnClickListener(this);
        }

        private void bind(Coordinador coordinador) {
            if (!TextUtils.isEmpty(coordinador.getImagen())){
                Picasso.with(context).load(coordinador.getImagen()).into(imagen);
            }else {
                imagen.setImageResource(R.color.fondo_imagen);
            }
            String nombre = coordinador.getNombre().trim() + " " + coordinador.getApellido();
            tv_nombre.setText(nombre);
        }


        @Override
        public void onClick(View view) {
            mOnClcikListener.onListItemClick(getItem(getAdapterPosition()), view);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        mOnDataChangeListener.onDataChangeClick(getItemCount());
    }
}
