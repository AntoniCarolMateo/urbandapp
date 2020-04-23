package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.List;

import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;
import cat.udl.urbandapp.utils.Utils;

public class UserViewModel extends AndroidViewModel {
    private UserServiceI repository;
    private MutableLiveData<String> responseLiveDataToken;
    private MutableLiveData<User> responseLiveUser;
    private MutableLiveData<Boolean> responseLiveRegister;
    private MutableLiveData<Boolean> responseLiveStep1;

    private MutableLiveData<List<User>> responseAllUsers;
    private SharedPreferences mPreferences;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();

        responseLiveDataToken = repository.getLiveDataToken();
        responseLiveUser = repository.getLiveDataUser();
        responseAllUsers = repository.getLiveDataAllUsers();
        responseLiveRegister = repository.getLiveDataRegister();
        responseLiveStep1 = repository.getLiveDataProfileStep1();

        this.mPreferences = PreferencesProvider.providePreferences();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registerUser(String username, String password,String gps){
        JsonObject user = new JsonObject();

        Log.d("UserViewModel", "user:" + username + " pass:"+ password);
        user.addProperty("username", "+34" + username);
        //tenemos que encriptar el password en sha-256 antes de enviarlo

        String salt = "16";
        String encode_hash = Utils.encode(password,salt,29000);
        user.addProperty("password",   encode_hash);
        user.addProperty("gps",gps);
        Log.d("UserRegister", "viewmodel");
        this.repository.registerUser(user);

    }

    public void  createTokenUser(String user, String password){
        String header = "34" + user + ":" + password;
        byte[] data = header.getBytes(StandardCharsets.UTF_8);
        header = Base64.encodeToString(data, Base64.DEFAULT);
        header = ("Authentication " + header).trim();
        repository.createTokenUser(header);

    }

    public void getProfileUser(){
        String header = this.mPreferences.getString("token","");
        repository.getProfileUser(header);
    }

    public void getAllUsers(){
        repository.getAllUsers();
    }

    public void setProfileInfo(String name, String surname, int exp, String birth, String gender, String desc) {
        String header = this.mPreferences.getString("token","");
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("surname", surname);
        json.addProperty("expirience", exp);
        json.addProperty("birthdate", birth);
        json.addProperty("gender", gender);
        json.addProperty("description", desc);


        repository.setProfileInfo(header, json);
    }



    public LiveData<String> getResponseLiveDataToken() {
        return this.responseLiveDataToken;
    }
    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }
    public LiveData<List<User>> getResponseLiveDataAllUsers() {
        return this.responseAllUsers;
    }
    public LiveData<Boolean> getResponseLiveDataRegister() {
        return this.responseLiveRegister;
    }
    public LiveData<Boolean> getResponseLiveDataProfileStep1() {
        return this.responseLiveStep1;
    }
}
