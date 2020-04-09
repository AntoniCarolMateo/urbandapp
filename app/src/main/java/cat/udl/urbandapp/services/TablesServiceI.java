package cat.udl.urbandapp.services;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TablesServiceI {

    MutableLiveData<List<Instrument>> getInstrumentsList();

    @POST("account/profile/table_instruments")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("account/profile/table_instruments")
    void setTableUserInstrument(@Body Instrument instrument);


}
