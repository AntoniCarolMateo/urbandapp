package cat.udl.urbandapp.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

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

public interface IUserDAO {
    /*
    @POST("users/register")
    Call<Void> registerUser(@Body JsonObject userJson);
    */

    @POST("users/register")
    Call<ResponseBody> registerUser(@Body JsonObject userJson);

    @POST("account/create_token")
    Call<ResponseBody> createTokenUser(@Header("Authorization") String auth);


    //@Jordi -> Por que no Call<User> o Call<List<User>> -> Retofit y GSON os hacen el trabajo sucio y no hace falta
    // que genereis el JSON a mano...

    @GET("account/profile")
    Call<ResponseBody> getProfileUser(@Header("Authorization") String auth);

    // @Jordi -> No me gusta el users/all -> Una mica toca punyetes... :)
    // M'agrada més fer /users -> Retorna tots els usuaris
    // Si voleu filtres podeu fer -> queda molt net fer:
    // Call <  List<Users>> getUsers(@Query("filter_param") String filter_param, @Header ("Authorization") String tokenAuth);
    // Si llamáis a getUsers(null, token) -> todos los usuarios
    // Su llamáis a getUsers(filter, token) -> lista filtrada
    // Muy limpio y muy faci de leer :)

    @GET("users/all")
    Call<ResponseBody> getAllUsers();


    @GET("users/get_info_by_subscription/{username}")
    Call<ResponseBody> getInfoSubscribed(@Header("Authorization") String auth, @Path("username") String username);

    @GET("users/subscribe/{username}")
    Call<ResponseBody> userSubscribe(@Header("Authorization") String auth, @Path("username") String username);

    @DELETE("users/delete_subscribed/{username}")
    Call<ResponseBody> userDeleteSubscribe(@Header("Authorization") String auth, @Path("username") String username);


    @POST("account/update_profile")
    Call<ResponseBody> setProfileInfo(@Header("Authorization") String Auth, @Body JsonObject json);

    @POST("account/profile/setfirstSetUp")
    Call<ResponseBody> firstTimeProfileSetUp(@Header ("Authorization") String Auth);

    @GET("account/profile/getfirstSetUp")
    Call<Boolean> getFirstTimeBoolean(@Header ("Authorization") String auth);

    @GET("account/profile/show")
    Call<User> showPrivateProfile(@Header ("Authorization") String auth);
}
