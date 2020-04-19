package cat.udl.urbandapp.services;

<<<<<<< Updated upstream
=======
import androidx.lifecycle.LiveData;
>>>>>>> Stashed changes
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
<<<<<<< Updated upstream
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
=======
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
>>>>>>> Stashed changes
import retrofit2.http.POST;

public interface TablesServiceI {

<<<<<<< Updated upstream
    MutableLiveData<List<Instrument>> getInstrumentsList();

    @POST("account/profile/table_instruments")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("account/profile/table_instruments")
    void setTableUserInstrument(@Body Instrument instrument);
=======
    @GET("users/profile/get_Table_instruments")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("users/profile/set_Table_instruments/addInstrument")
    void addInstrument(@Header("Authorization")String Auth, @Body JsonObject instrument);

    @POST("users/profile/deleteInstrument")
    void removeInstrument(@Header("Authorization")String Auth, @Body JsonObject instrument);

    MutableLiveData<User> getLiveDataUser();

    LiveData<List<Instrument>> getTableInstruments();
>>>>>>> Stashed changes


}
