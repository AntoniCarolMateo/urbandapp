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
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TablesServiceI {


    @GET("/users/profile/instruments/list")
    void getTableUserInstrument(@Header("Authorization") String Auth);

    @POST("/users/profile/instruments/add")
    void addInstrument(@Header("Authorization") String Auth, @Body List<Instrument> instrument);

    @POST("users/profile/deleteInstrument")
    void removeInstrument(@Header("Authorization") String Auth, @Query("name") String nameInstrument);

    @DELETE("/users/profile/instruments/delete/{name}")
    void addGenere (@Header("Authorization") String auth, @Path("name") String nameGenere);

    @POST("users/profile/addMultipleGeneres")
    void removeMultipleGeneres(@Header("Authorization") String auth, @Body List<String> list_generes);

    @POST("users/profile/deleteGenere/{name}")
    void removeGenere(@Header("Authorization") String auth, @Path("name") String nameGenere);


    @GET("users/profile/getGeneresList")
    void getTableUserGenere(@Header("Authorization") String auth);

    MutableLiveData<User> getLiveDataUser();

    LiveData<List<Instrument>> getTableInstruments();

    MutableLiveData<Boolean> getLiveDataAddedIns();

    MutableLiveData<Boolean> getLiveDataRemoveIns();
}
