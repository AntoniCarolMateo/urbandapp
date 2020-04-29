package cat.udl.urbandapp.models;

import androidx.annotation.NonNull;
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
        return this.name.equals(e.getNameInstrument());

    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name;
    }
}
