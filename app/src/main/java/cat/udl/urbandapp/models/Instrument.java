package cat.udl.urbandapp.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Instrument {

    @SerializedName("id_instrument")
    private int idInstrument;


    @SerializedName("name")
    private String name;

    @SerializedName("expirience")
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

    public int getIdInstrument() {
        return idInstrument;
    }

    public void setIdInstrument(int idInstrument) {
        this.idInstrument = idInstrument;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("expirience", this.expirience);
        return json;
    }
}



