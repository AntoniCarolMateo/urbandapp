package cat.udl.urbandapp.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instrument {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("expirience")
    @Expose
    private int expirience;

    public Instrument(){

    }
    public Instrument(String nameInstrument, int expirience) {
        this.name = nameInstrument;
        this.expirience = expirience;
    }

    public String getNameInstrument() {
        return name;
    }

    public void setNameInstrument(String nameInstrument) {
        this.name = nameInstrument;
    }

    public int getExpirience() {
        return expirience;
    }

    public void setExpirience(int expirience) {
        this.expirience = expirience;
    }


}



