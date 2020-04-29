package cat.udl.urbandapp.dao;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

    @POST("account/update_profile")
    Call<ResponseBody> setProfileInfo(@Header("Authorization") String Auth, @Body JsonObject json);
}
