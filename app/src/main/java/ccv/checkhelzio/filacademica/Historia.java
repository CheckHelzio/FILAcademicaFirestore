package ccv.checkhelzio.filacademica;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by check on 17/11/2017.
 */

class Historia implements Parcelable {
    private String descripcion;
    private String titulo;
    private int prioridad;
    private boolean justificado;

    public Historia() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isJustificado() {
        return justificado;
    }

    public void setJustificado(boolean justificado) {
        this.justificado = justificado;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.descripcion);
        dest.writeString(this.titulo);
        dest.writeInt(this.prioridad);
        dest.writeByte(this.justificado ? (byte) 1 : (byte) 0);
    }

    protected Historia(Parcel in) {
        this.descripcion = in.readString();
        this.titulo = in.readString();
        this.prioridad = in.readInt();
        this.justificado = in.readByte() != 0;
    }

    public static final Creator<Historia> CREATOR = new Creator<Historia>() {
        @Override
        public Historia createFromParcel(Parcel source) {
            return new Historia(source);
        }

        @Override
        public Historia[] newArray(int size) {
            return new Historia[size];
        }
    };
}
