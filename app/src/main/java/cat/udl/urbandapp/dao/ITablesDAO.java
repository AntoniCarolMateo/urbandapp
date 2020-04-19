package cat.udl.urbandapp.dao;
<<<<<<< Updated upstream
import com.google.gson.JsonObject;

import org.json.JSONObject;
=======

import com.google.gson.JsonObject;
>>>>>>> Stashed changes

import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
<<<<<<< Updated upstream
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ITablesDAO {

    @GET("account/profile/get_instruments_table")
    Call<ResponseBody> getInstrumentsTable(@Header("Authorization") String auth);
    @POST("account/profile/set_table_instruments")
    Call<Void> setTableUserInstrument(@Body Instrument instruments);



=======
import retrofit2.http.POST;

public interface ITablesDAO {
    @POST("users/profile/addInstrument")
    Call<Void> addInstrument(@Header("Authorization") String auth, @Body JsonObject instruments);

    @GET("users/profile/getInstrumentsList")
    Call<ResponseBody> getTableUserInstrument(@Header("Authorization") String auth);

    @POST("users/profile/deleteInstrument")
    Call<Void> removeInstrument(@Header("Authorization") String auth, @Body JsonObject instrument);
>>>>>>> Stashed changes
}
