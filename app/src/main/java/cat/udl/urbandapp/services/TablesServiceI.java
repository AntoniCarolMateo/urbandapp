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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TablesServiceI {

    //----------------------------------------------------------INSTRUMENTS
    @GET("users/profile/instruments/list")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("users/profile/instruments/add")
    void addInstrument(@Header("Authorization") String Auth, @Body JsonObject instrument);

    @POST("users/profile/instruments/delete/{name}")
    void removeInstrument(@Header("Authorization") String Auth, @Query("name") String nameInstrument);

    //----------------------------------------------------------GENERES
    @POST("users/profile/musical_genres/add/{name}")
    void addGenere (@Header("Authorization") String auth, @Path("name") String nameGenere);

    @POST("users/profile/musical_genres/add")
    void addMultipleGeneres(@Header("Authorization") String auth, @Body List<String> list_generes);

    @POST("users/profile/musical_genres/delete/{name}")
    void removeGenere(@Header("Authorization") String auth, @Path("name") String nameGenere);


    @GET("users/profile/musical_genres/list")
    void getTableUserGenere(@Header("Authorization") String auth);

    MutableLiveData<User> getLiveDataUser();

    LiveData<List<Instrument>> getTableInstruments();

    MutableLiveData<Boolean> getLiveDataAddedIns();

    MutableLiveData<Boolean> getLiveDataRemoveIns();
}
