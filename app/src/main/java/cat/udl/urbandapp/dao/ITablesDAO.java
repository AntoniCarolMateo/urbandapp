package cat.udl.urbandapp.dao;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ITablesDAO {

    @GET("account/profile/get_instruments_table")
    Call<ResponseBody> getInstrumentsTable(@Header("Authorization") String auth);
    @POST("account/profile/set_table_instruments")
    Call<Void> setTableUserInstrument(@Body Instrument instruments);



}
