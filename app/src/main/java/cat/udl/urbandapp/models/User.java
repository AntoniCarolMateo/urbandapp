package cat.udl.urbandapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("genere")
    private String genere;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;



    @SerializedName("latitude")
    private float latitude;

    @SerializedName("longitude")
    private float longitude;

    ArrayList<Instrument> userInstruments;

    public User() {
    }


    public User(String created_at, String username, String email, String name, String surname, String birthday, String genere) {
        this.created_at = created_at;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.genere = genere;
    }

    public User(String created_at, String username, String email, String name, String surname, String birthday, String genere, float latitude, float longitude) {
        this.created_at = created_at;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.genere = genere;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ArrayList<Instrument> getUserInstruments() {
        return userInstruments;
    }

    public void setUserInstruments(ArrayList<Instrument> listInstruments) {
        this.userInstruments = listInstruments;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @NonNull
    @Override
    public String toString(){
        return this.name + " " + this.surname;
    }
}
