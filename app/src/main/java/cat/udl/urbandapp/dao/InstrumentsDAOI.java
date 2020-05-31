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

public interface InstrumentsDAOI {
    //----------------------------------------------------------INSTRUMENTS
    @POST("/users/profile/instruments/add")
    Call<ResponseBody> addInstrument(@Header("Authorization") String auth, @Body List<Instrument> instruments);

    @GET("/users/profile/instruments/list")
    Call<List<Instrument>> getTableUserInstrument(@Header("Authorization") String auth);

    @DELETE("/users/profile/instruments/delete/{name}")
    Call<ResponseBody> removeInstrument(@Header("Authorization") String auth, @Path("name") String nameInstrument);
}
