package cat.udl.urbandapp.services;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserServiceI {
    @POST("users/register")
    void registerUser(@Body JsonObject userJson);



    @POST("account/create_token")
    void createTokenUser(@Header("Authorization") String auth);

    @POST("account/profile")
    void getProfileUser(@Header("Authorization") String auth);

    MutableLiveData<String> getLiveDataToken();

    MutableLiveData<User> getLiveDataUser();

}
