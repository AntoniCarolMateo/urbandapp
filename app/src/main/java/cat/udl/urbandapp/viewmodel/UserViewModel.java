package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class UserViewModel extends AndroidViewModel {
    private UserServiceI repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();



    }
    public void registerUser(String password, String username){
        JsonObject user = new JsonObject();

        Log.d("UserViewModel",password + " "+ username);
        user.addProperty("username", "+34" + username);
        user.addProperty("password",   password);
        this.repository.registerUser(user);

    }

}
