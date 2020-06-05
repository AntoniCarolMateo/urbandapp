package cat.udl.urbandapp.models;


import cat.udl.urbandapp.R;

public enum RolEnum {
    user("user","user"),band("band","band"),sponsor("sponsor","sponsor");

    String id;
    String name;

    RolEnum(String _id, String _name){
        id = _id;
        name = _name;
    }

    public static RolEnum getRolByName(String rol) {
        switch (rol) {
            case "user":
                return RolEnum.user;
            case "band":
                return RolEnum.band;
            case "sponsor":
                return RolEnum.sponsor;
            default:
                return null;
        }
    }

    public String getName(){
        return name;
    }

    public static int getImageResource(RolEnum e){

        switch (e){
            case user:
                return R.drawable.musician;
            case band:
                return R.drawable.band;
            case sponsor:
                return R.drawable.sponsor;
            default:
                return -1;
        }
    }

}
