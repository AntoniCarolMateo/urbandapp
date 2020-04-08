package cat.udl.urbandapp.models;

import com.google.gson.annotations.SerializedName;

public class Instrument {

        @SerializedName("nameInstrument")
        private String nameInstrument;

        @SerializedName("expirience")
        private int expirience;

        public Instrument(String n, int e) {
            this.nameInstrument = n;
            this.expirience = e;
        }

        public String getNameInstrument() {
            return nameInstrument;
        }

        public void setNameInstrument(String nameInstrument) {
            this.nameInstrument = nameInstrument;
        }

        public int getExpierence() {
            return expirience;
        }

        public void setExpierence(int expierence) {
            this.expirience = expierence;
        }

        public String toString(){
            return "Intrumento: " + this.nameInstrument + ", Exp : " + this.expirience;
        }
    }



