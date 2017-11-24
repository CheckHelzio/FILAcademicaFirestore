package ccv.checkhelzio.filacademica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

/**
 * Created by check on 09/11/2017.
 */

class ActividadesAdapter extends FirestoreRecyclerAdapter<Actividad, ActividadesAdapter.ActividadesViewHolder> {


    final private ListItemClickListener mOnClcikListener;
    private Context context;

    public interface ListItemClickListener {
        void onListItemClick(Actividad clickedItemIndex, View view);
    }

    public ActividadesAdapter(Context context, FirestoreRecyclerOptions<Actividad> options, ListItemClickListener listItemClickListener) {
        super(options);
        this.context = context;
        mOnClcikListener = listItemClickListener;
    }


    @Override
    public ActividadesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.actividades_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ActividadesViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ActividadesViewHolder holder, int position, Actividad model) {
        holder.bind(model);
    }

    class ActividadesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView cartel;

        ActividadesViewHolder(View itemView) {
            super(itemView);
            cartel = itemView.findViewById(R.id.cartel);
            itemView.setOnClickListener(this);
        }

        private void bind(Actividad actividades) {
            Picasso.with(context).load(actividades.getCartel()).into(cartel);
        }


        @Override
        public void onClick(View view) {
            mOnClcikListener.onListItemClick(getItem(getAdapterPosition()), view);
        }
    }
}
