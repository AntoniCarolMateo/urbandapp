package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserDAO {
    @POST("users/register")
    Call<Void> registerUser(@Body JsonObject userJson);

    @POST("account/create_token")
    Call<Void> createTokenUser(@Header("Authorization") String auth);
}
