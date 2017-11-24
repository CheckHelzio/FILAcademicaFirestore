package ccv.checkhelzio.filacademica;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


class Sede implements Parcelable {
    private String nombre;
    private String direccion;
    private GeoPoint lugar;
    private LatLng lugar_lat_lng;
    private String imagen;

    public Sede() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public GeoPoint getLugar() {
        return lugar;
    }

    public void setLugar(GeoPoint lugar) {
        lugar_lat_lng = new LatLng(lugar.getLatitude(), lugar.getLongitude());
        this.lugar = lugar;
    }

    public LatLng getLugar_lat_lng() {

        return lugar_lat_lng;
    }

    public void setLugar_lat_lng(LatLng lugar_lat_lng) {
        this.lugar_lat_lng = lugar_lat_lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.direccion);
        dest.writeParcelable(this.lugar_lat_lng, flags);
        dest.writeString(this.imagen);
    }

    protected Sede(Parcel in) {
        this.nombre = in.readString();
        this.direccion = in.readString();
        this.lugar_lat_lng = in.readParcelable(LatLng.class.getClassLoader());
        this.imagen = in.readString();
    }

    public static final Creator<Sede> CREATOR = new Creator<Sede>() {
        @Override
        public Sede createFromParcel(Parcel source) {
            return new Sede(source);
        }

        @Override
        public Sede[] newArray(int size) {
            return new Sede[size];
        }
    };
}
