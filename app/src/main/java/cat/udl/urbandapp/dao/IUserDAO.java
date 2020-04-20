package cat.udl.urbandapp.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserDAO {
    @POST("users/register")
    Call<Void> registerUser(@Body JsonObject userJson);

    @POST("account/create_token")
    Call<ResponseBody> createTokenUser(@Header("Authorization") String auth);

    @GET("account/profile")
    Call<ResponseBody> getProfileUser(@Header("Authorization") String auth);

    @POST("account/firstTimeLogged")
    Call<ResponseBody> firstTimeLogged(@Header("Authorization") String auth);

    @POST("account/profile/setProfileStep1")
    Call<Void> setProfileInfo(@Header("Authorization") String Auth,@Body JsonObject json);
}


