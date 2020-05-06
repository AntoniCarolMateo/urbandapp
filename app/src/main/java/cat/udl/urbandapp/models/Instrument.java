package cat.udl.urbandapp.models;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Instrument {

    @SerializedName("id_instrument")
    private int idInstrument;


    @SerializedName("name")
    private String name;

    @SerializedName("expirience")
    private float expirience;

    public Instrument(){

    }
    public Instrument(String nameInstrument, float expirience) {
        this.name = nameInstrument;
        this.expirience = expirience;
    }

    public String getNameInstrument() {
        return name;
    }

    public void setNameInstrument(String nameInstrument) {
        this.name = nameInstrument;
    }

    public float getExpirience() {
        return expirience;
    }

    public void setExpirience(float expirience) {
        this.expirience = expirience;
    }

    public int getIdInstrument() {
        return idInstrument;
    }

    public void setIdInstrument(int idInstrument) {
        this.idInstrument = idInstrument;
    }
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Instrument)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Instrument e = (Instrument) o;

        // Compare the data members and return accordingly
        return this.name.equals(e.getNameInstrument())
                && this.expirience == e.getExpirience();

    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name +" Experience: " +expirience;
    }
}



