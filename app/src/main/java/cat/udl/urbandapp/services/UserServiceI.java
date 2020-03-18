package cat.udl.urbandapp.services;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserServiceI {
    @POST("users/register")
    void registerUser(@Body JsonObject userJson);

    @POST("account/create_token")
    String createTokenUser(@Header("Authorization") String auth);

}
