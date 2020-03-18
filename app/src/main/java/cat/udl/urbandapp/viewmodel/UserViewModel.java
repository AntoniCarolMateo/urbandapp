package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;
import cat.udl.urbandapp.utils.Utils;

public class UserViewModel extends AndroidViewModel {
    private UserServiceI repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();



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

    public void createTokenUser(String user, String pass){
        String header= "Token 34" + user + ":" + pass;
        this.repository.createTokenUser(header);
    }

}
