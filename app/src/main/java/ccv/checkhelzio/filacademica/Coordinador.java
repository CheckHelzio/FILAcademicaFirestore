package ccv.checkhelzio.filacademica;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by check on 14/11/2017.
 */

class Coordinador implements Parcelable {

    private String nombre;
    private String apellido;
    private String imagen;
    private ArrayList<String> descripcion;


    public Coordinador() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public ArrayList<String> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(ArrayList<String> descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.imagen);
        dest.writeStringList(this.descripcion);
    }

    protected Coordinador(Parcel in) {
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.imagen = in.readString();
        this.descripcion = in.createStringArrayList();
    }

    public static final Creator<Coordinador> CREATOR = new Creator<Coordinador>() {
        @Override
        public Coordinador createFromParcel(Parcel source) {
            return new Coordinador(source);
        }

        @Override
        public Coordinador[] newArray(int size) {
            return new Coordinador[size];
        }
    };
}
