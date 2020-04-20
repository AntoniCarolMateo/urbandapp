package cat.udl.urbandapp.services;


import androidx.lifecycle.LiveData;

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

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;

public interface TablesServiceI {


    @GET("account/profile/table_instruments")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("users/profile/set_Table_instruments/addInstrument")
    void addInstrument(@Header("Authorization")String Auth, @Body JsonObject instrument);

    @POST("users/profile/deleteInstrument")
    void removeInstrument(@Header("Authorization")String Auth, @Body JsonObject instrument);

    MutableLiveData<User> getLiveDataUser();

    LiveData<List<Instrument>> getTableInstruments();


}
