package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ILoginDA0 {

    @POST("users/register")
    Call<ResponseBody> registerUser(@Body JsonObject userJson);

    @POST("account/create_token")
    Call<ResponseBody> createTokenUser(@Header("Authorization") String auth);

}
