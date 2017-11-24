package ccv.checkhelzio.filacademica;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by check on 13/11/2017.
 */

class Sesiones implements Parcelable {

    private String fecha;
    private String hora;
    private String lugar;

    public Sesiones() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fecha);
        dest.writeString(this.hora);
        dest.writeString(this.lugar);
    }

    protected Sesiones(Parcel in) {
        this.fecha = in.readString();
        this.hora = in.readString();
        this.lugar = in.readString();
    }

    public static final Parcelable.Creator<Sesiones> CREATOR = new Parcelable.Creator<Sesiones>() {
        @Override
        public Sesiones createFromParcel(Parcel source) {
            return new Sesiones(source);
        }

        @Override
        public Sesiones[] newArray(int size) {
            return new Sesiones[size];
        }
    };

    @Override
    public String toString() {
        return "Sesiones{" +
                "fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
