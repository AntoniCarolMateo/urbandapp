package cat.udl.urbandapp.services;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceI {
    /*
    @POST("users/register")
    void registerUser(@Body JsonObject userJson);
    */
    @POST("users/register")
    void registerUser(@Body JsonObject userJson);


    @POST("account/create_token")
    void createTokenUser(@Header("Authorization") String auth);

    @POST("account/profile")
    void getProfileUser(@Header("Authorization") String auth);


    @GET("users/all")
    void getAllUsers();


    @POST("account/update_profile")
    void setProfileInfo(String header, JsonObject json);


    @GET("users/get_info_by_subscription/{username}")
    void getInfoSubscribed(@Header("Authorization") String auth, @Path("username") String username);

    @GET("users/subscribe/{username}")
    void userSubscribe(@Header("Authorization") String auth, @Path("username") String username);

    @DELETE("users/delete_subscribed/{username}")
    void userDeleteSubscribe(@Header("Authorization") String auth, @Path("username") String username);

    @POST("account/profile/setfirstSetUp")
    void firstTimeProfileSetUp(@Header ("Authorization") String Auth);

    @GET("account/profile/getfirstSetUp")
    void getFirstTimeBoolean(@Header ("Authorization") String auth);

    MutableLiveData<String> getLiveDataToken();

    MutableLiveData<User> getLiveDataUser();

    MutableLiveData<User> getLiveDataUserSubscription();

    MutableLiveData<List<User>> getLiveDataAllUsers();

    MutableLiveData<Boolean> getLiveDataRegister();

    MutableLiveData<Boolean> getLiveDataProfileStep1();

    MutableLiveData<Boolean> getLiveDataFirstTime();

    MutableLiveData<Boolean> getLiveDataSubscription();

    MutableLiveData<Boolean> getLiveDataDeleteSubscription();
}

