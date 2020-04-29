package cat.udl.urbandapp.dao;


import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ITablesDAO {
    //----------------------------------------------------------INSTRUMENTS
    @POST("/users/profile/instruments/add")
    Call<ResponseBody> addInstrument(@Header("Authorization") String auth, @Body List<Instrument> instruments);

    @GET("/users/profile/instruments/list")
    Call<List<Instrument>> getTableUserInstrument(@Header("Authorization") String auth);

    @DELETE("/users/profile/instruments/delete/{name}")
    Call<ResponseBody> removeInstrument(@Header("Authorization") String auth, @Path("name") String nameInstrument);

    //----------------------------------------------------------GENERES
    @POST("users/profile/addGenere/{name}")
    Call<ResponseBody> addGenere (@Header("Authorization") String auth, @Path("name") String nameGenere);

    @POST("users/profile/addMultipleGeneres")
    Call<ResponseBody> addMultipleGeneres(@Header("Authorization") String auth, @Body List<String> list_generes);

    @POST("users/profile/deleteGenere/{name}")
    Call<ResponseBody> removeGenere(@Header("Authorization") String auth, @Path("name") String nameGenere);

    @GET("users/profile/getGeneresList")
    Call<ResponseBody> getTableUserGenere(@Header("Authorization") String auth);
}
