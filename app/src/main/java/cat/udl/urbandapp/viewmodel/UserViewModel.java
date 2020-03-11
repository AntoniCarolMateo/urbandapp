package cat.udl.urbandapp.viewmodel;

import android.app.Application;

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
    public void registerUser(String username, String password){
        JSONObject user = new JSONObject();
        try {
            user.put("username", "+34" + username);
            user.put("password",   password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.repository.registerUser(user);

    }

}
