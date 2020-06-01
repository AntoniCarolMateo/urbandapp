package cat.udl.urbandapp.dao;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserDAO {

    @GET("account/profile")
    Call<ResponseBody> getProfileUser(@Header("Authorization") String auth);

    @GET("users/all")
    Call<ResponseBody> getAllUsers(@Header("Authorization") String auth);

    @GET("users/match")
    Call<User> getMatch(@Header("Authorization") String auth);

    @GET("users/get_info_by_subscription/{username}")
    Call<ResponseBody> getInfoSubscribed(@Header("Authorization") String auth, @Path("username") String username);

    @GET("users/subscribe/{username}")
    Call<ResponseBody> userSubscribe(@Header("Authorization") String auth, @Path("username") String username);

    @DELETE("users/delete_subscribed/{username}")
    Call<ResponseBody> userDeleteSubscribe(@Header("Authorization") String auth, @Path("username") String username);

    @POST("account/profile/setfirstSetUp")
    Call<ResponseBody> firstTimeProfileSetUp(@Header ("Authorization") String Auth);

    @GET("account/profile/getfirstSetUp")
    Call<Boolean> getFirstTimeBoolean(@Header ("Authorization") String auth);

    @GET("account/profile/show")
    Call<User> showPrivateProfile(@Header ("Authorization") String auth);


    @GET("users/all")
    Call <List<User>> getFilteredUsers(@Header("Authorization") String auth, @Nullable
                                       @Query("instruments") List<String> instruments, @Nullable @Query("genres")List<String> genres);



}
