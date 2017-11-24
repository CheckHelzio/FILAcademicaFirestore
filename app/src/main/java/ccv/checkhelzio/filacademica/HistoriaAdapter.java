package ccv.checkhelzio.filacademica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

/**
 * Created by check on 09/11/2017.
 */

class HistoriaAdapter extends FirestoreRecyclerAdapter<Historia, HistoriaAdapter.HistoriaViewHolder> {

    final private DataChangeListener mOnDataChangeListener;

    public interface DataChangeListener {
        void onDataChangeClick(int itemCount);
    }

    public HistoriaAdapter(Context context, FirestoreRecyclerOptions<Historia> options, DataChangeListener onDataChangeListener) {
        super(options);
        mOnDataChangeListener = onDataChangeListener;
    }

    @Override
    public HistoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.historia_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new HistoriaViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(HistoriaViewHolder holder, int position, Historia model) {
        holder.bind(model);
    }

    class HistoriaViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_titulo;
        private TextViewEx tv_descripcion;

        HistoriaViewHolder(View itemView) {
            super(itemView);
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
        }

        private void bind(Historia historia) {
            tv_titulo.setText(historia.getTitulo());
            String des = historia.getDescripcion();

            des = des.replaceAll("\\\n", "\n");
            des = des.replaceAll("\\\\n", "\n");

            if (historia.isJustificado()){
                tv_descripcion.setText(des, true);
            }else {
                tv_descripcion.setText(des, false);
            }

        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        mOnDataChangeListener.onDataChangeClick(getItemCount());
    }
}
