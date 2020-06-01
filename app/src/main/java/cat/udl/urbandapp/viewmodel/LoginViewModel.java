package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;

import cat.udl.urbandapp.services.LoginServiceImpl;
import cat.udl.urbandapp.utils.Utils;

public class LoginViewModel extends AndroidViewModel {

    private LoginServiceImpl repository;

    private MutableLiveData<String> responseLiveDataToken;
    private MutableLiveData<Boolean> responseLiveRegister;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginServiceImpl();
        responseLiveDataToken = repository.getLiveDataToken();
        responseLiveRegister = repository.getLiveDataRegister();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registerUser(String username, String password,String gps){
        JsonObject user = new JsonObject();

        Log.d("UserViewModel", "user:" + username + " pass:"+ password);
        user.addProperty("username", "+34" + username);
        String salt = "16";
        String encode_hash = Utils.encode(password,salt,29000);
        user.addProperty("password",   encode_hash);
        user.addProperty("gps",gps);
        Log.d("UserRegister", "viewmodel");
        this.repository.registerUser(user);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void  createTokenUser(String user, String password){
        String header = "34" + user + ":" + password;
        byte[] data = header.getBytes(StandardCharsets.UTF_8);
        header = Base64.encodeToString(data, Base64.DEFAULT);
        header = ("Authentication " + header).trim();
        repository.createTokenUser(header);

    }

    public LiveData<String> getResponseLiveDataToken() {
        return this.responseLiveDataToken;
    }
    public LiveData<Boolean> getResponseLiveDataRegister() {
        return this.responseLiveRegister;
    }
}
