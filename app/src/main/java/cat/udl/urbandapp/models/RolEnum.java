package cat.udl.urbandapp.models;

import android.widget.Switch;

import cat.udl.urbandapp.R;

public enum RolEnum {
    SOLO("user","user"),BAND("band","band"),SPONSOR("sponsor","sponsor");

    String id;
    String name;

    RolEnum(String _id, String _name){
        id = _id;
        name = _name;
    }

    public String getName(){
        return name;
    }

    public static int getImageResource(RolEnum e){

        switch (e){
            case SOLO:
                return R.drawable.musician;
            case BAND:
                return R.drawable.band;
            case SPONSOR:
                return R.drawable.sponsor;
            default:
                return -1;
        }
    }

}
