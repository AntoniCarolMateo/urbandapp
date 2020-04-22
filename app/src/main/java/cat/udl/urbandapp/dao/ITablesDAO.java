package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import com.google.gson.JsonObject;


import cat.udl.urbandapp.models.Instrument;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ITablesDAO {
    @POST("users/profile/addInstrument")
    Call<Void> addInstrument(@Header("Authorization") String auth, @Body JsonObject instruments);

    @GET("users/profile/getInstrumentsList")
    Call<ResponseBody> getTableUserInstrument(@Header("Authorization") String auth);

    @POST("users/profile/deleteInstrument")
    Call<Void> removeInstrument(@Header("Authorization") String auth, @Body JsonObject instrument);

}
