package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.models.RolEnum;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IProfileSetUpDAO {

    @POST("account/update_profile")
    Call<ResponseBody> setProfileInfo(@Header("Authorization") String Auth, @Body JsonObject json);

    @POST("account/profile/setUsername/{username}")
    Call<ResponseBody> setUsername(@Header ("Authorization") String auth, @Path("username") String username);

    @POST("account/profile/setUserRol/{rol}")
    Call<ResponseBody> setUserRol(@Header("Authorization")String auth,  @Path("rol") RolEnum rol);
}
