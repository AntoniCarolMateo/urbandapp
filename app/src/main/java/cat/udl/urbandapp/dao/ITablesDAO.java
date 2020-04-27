package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import com.google.gson.JsonObject;


import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ITablesDAO {
    //----------------------------------------------------------INSTRUMENTS
    @POST("users/profile/instruments/add")
    Call<ResponseBody> addInstrument(@Header("Authorization") String auth, @Body JsonObject instruments);

    @GET("users/profile/instruments/list")
    Call<ResponseBody> getTableUserInstrument(@Header("Authorization") String auth);

    @POST("users/profile/instruments/delete/{name}")
    Call<ResponseBody> removeInstrument(@Header("Authorization") String auth, @Path("name") String nameInstrument);

    //----------------------------------------------------------GENERES
    @POST("users/profile/muscial_genres/add/{name}")
    Call<ResponseBody> addGenere (@Header("Authorization") String auth, @Path("name") String nameGenere);

    @POST("users/profile/musical_genres/add")
    Call<ResponseBody> addMultipleGeneres(@Header("Authorization") String auth, @Body List<String> list_generes);

    @POST("users/profile/musical_genres/delete/{name}")
    Call<ResponseBody> removeGenere(@Header("Authorization") String auth, @Path("name") String nameGenere);

    @GET("users/profile/musical_genres/list")
    Call<ResponseBody> getTableUserGenere(@Header("Authorization") String auth);
}
