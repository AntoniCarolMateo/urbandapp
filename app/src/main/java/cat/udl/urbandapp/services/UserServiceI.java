package cat.udl.urbandapp.services;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServiceI {
    @POST("users/register")
    void registerUser(@Body JSONObject userJson);
}
