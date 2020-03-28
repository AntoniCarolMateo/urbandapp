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

import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;
import cat.udl.urbandapp.utils.Utils;

public class UserViewModel extends AndroidViewModel {
    private UserServiceI repository;
    private MutableLiveData<String> responseLiveDataToken;
    private MutableLiveData<User> responseLiveUser;
    private SharedPreferences mPreferences;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();

        responseLiveDataToken = repository.getLiveDataToken();
        responseLiveUser = repository.getLiveDataUser();
        this.mPreferences = PreferencesProvider.providePreferences();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registerUser(String username, String password){
        JsonObject user = new JsonObject();

        Log.d("UserViewModel", "user:" + username + " pass:"+ password);
        user.addProperty("username", "+34" + username);
        //tenemos que encriptar el password en sha-256 antes de enviarlo

        String salt = "16";
        String encode_hash = Utils.encode(password,salt,29000);
        user.addProperty("password",   encode_hash);
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
       // String header = "34" + user + ":" + password;
        String header = this.mPreferences.getString("token","");
        /*
        byte[] data = header.getBytes(StandardCharsets.UTF_8);
        header = Base64.encodeToString(data, Base64.DEFAULT);
        */
        repository.getProfileUser(header);

    }

    public LiveData<String> getResponseLiveDataToken() {
        return this.responseLiveDataToken;
    }
    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }

}
