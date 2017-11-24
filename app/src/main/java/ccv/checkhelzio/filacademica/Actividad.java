package ccv.checkhelzio.filacademica;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by check on 09/11/2017.
 */

public class Actividad implements Parcelable {
    private String titulo;
    private ArrayList<Coordinador> coordinador;
    private String cartel;
    private ArrayList<Sesiones> sesiones;
    private ArrayList<Sede> sedes;

    public Actividad() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Coordinador> getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(ArrayList<Coordinador> coordinador) {
        this.coordinador = coordinador;
    }

    public String getCartel() {
        return cartel;
    }

    public void setCartel(String cartel) {
        this.cartel = cartel;
    }

    public ArrayList<Sesiones> getSesiones() {
        return sesiones;
    }

    public void setSesiones(ArrayList<Sesiones> sesiones) {
        this.sesiones = sesiones;
    }

    public ArrayList<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(ArrayList<Sede> sedes) {
        this.sedes = sedes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titulo);
        dest.writeTypedList(this.coordinador);
        dest.writeString(this.cartel);
        dest.writeTypedList(this.sesiones);
        dest.writeList(this.sedes);
    }

    protected Actividad(Parcel in) {
        this.titulo = in.readString();
        this.coordinador = in.createTypedArrayList(Coordinador.CREATOR);
        this.cartel = in.readString();
        this.sesiones = in.createTypedArrayList(Sesiones.CREATOR);
        this.sedes = new ArrayList<Sede>();
        in.readList(this.sedes, Sede.class.getClassLoader());
    }

    public static final Creator<Actividad> CREATOR = new Creator<Actividad>() {
        @Override
        public Actividad createFromParcel(Parcel source) {
            return new Actividad(source);
        }

        @Override
        public Actividad[] newArray(int size) {
            return new Actividad[size];
        }
    };
}
