package cat.udl.urbandapp.models;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.SerializedName;

public class MusicalGenere {

    @SerializedName("name")
    private String name;

    public MusicalGenere(){

    }

    public MusicalGenere(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
