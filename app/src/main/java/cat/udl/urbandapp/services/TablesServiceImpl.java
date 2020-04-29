package cat.udl.urbandapp.services;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;

import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.dao.IUserDAO;
import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.dao.UserDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TablesServiceImpl implements TablesServiceI {

    private TablesDAOImpl tablesDAO;

    public MutableLiveData<List<Instrument>> allInsruments;
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    public final MutableLiveData<User> mUser;
    public final MutableLiveData<List<Instrument>> mlistInstruent;
    public final MutableLiveData<List<MusicalGenere>> mlistGeneres;
    public final MutableLiveData<Boolean> mResponseWorked;


    public TablesServiceImpl() {
        tablesDAO = new TablesDAOImpl();
        mUser = new MutableLiveData<>();
        mlistInstruent = new MutableLiveData<>();
        mlistGeneres = new MutableLiveData<>();
        mResponseWorked = new MutableLiveData<>();

    }

    @Override
    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }

    @Override
    public LiveData<List<Instrument>> getTableInstruments() {
        return mlistInstruent;
    }

    @Override
    public MutableLiveData<Boolean> getLiveWorkedOrNot() {
        return mResponseWorked;
    }


    @Override
    public LiveData<List<MusicalGenere>> getTableGeneres() {
        return mlistGeneres;
    }


    @Override
    public void getTableUserInstrument(final String Auth) {
        tablesDAO.getTableUserInstrument(Auth).enqueue(new Callback<List<Instrument>>() {
            @Override
            public void onResponse(Call<List<Instrument>> call, Response<List<Instrument>> response) {
                if (response.code() == 200) {
                    List<Instrument> mList = new ArrayList<>();
                    mList = response.body();
                    mlistInstruent.setValue(mList);
                    Log.d("getUserInstruments", "Number of instruments: " + mList.size());
                } else {
                    mlistInstruent.setValue(new ArrayList<Instrument>());
                    Log.d("getUser", "Error " + response.code() + " message:" + response.message());
                    Log.d("getUser", "header es: " + Auth);

                }
            }

            @Override
            public void onFailure(Call<List<Instrument>> call, Throwable t) {
                Log.d("getInstrumentUserList", t.getMessage().toString());
            }
        });
    }


    @Override
    public void addInstrument(String Auth, List<Instrument> instrument) {
        tablesDAO.addInstrument(Auth, instrument).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Add Instrument", ""+call.request().url());
                if (response.code() == 200){
                    mResponseWorked.setValue(true);
                    Log.d("Add Instrument", "Added succesfully");
                }else{
                    mResponseWorked.setValue(false);
                    Log.d("Add Instrument", ""+response.code()+response.message());
                    Log.d("Add Instrument", "Failed to Add instrument");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Add Instrument", t.getMessage().toString());

            }
        });
    }

    @Override
    public void removeInstrument(String Auth, String instrument) {
        tablesDAO.removeInstrument(Auth, instrument).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){
                    mResponseWorked.setValue(true);
                    Log.d("Remove Instrument", "Removed succesfully");
                }else{
                    mResponseWorked.setValue(false);
                    Log.d("Remove Instrument", "Failed to Add instrument");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("removeInstrument", t.getMessage().toString());
            }
        });
    }

    @Override
    public void addGenere(String auth, List<MusicalGenere> list_generes) {
        tablesDAO.addGenere(auth, list_generes).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    mResponseWorked.setValue(true);
                }else{
                    mResponseWorked.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseWorked.setValue(false);
            }
        });
    }

    @Override
    public void removeGenere(String auth, String nameGenere) {
        tablesDAO.removeGenere(auth, nameGenere).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    mResponseWorked.setValue(true);
                }else{
                    mResponseWorked.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseWorked.setValue(false);
            }
        });
    }

    @Override
    public void getTableUserGenere(final String auth) {
        tablesDAO.getTableUserGenere(auth).enqueue(new Callback<List<MusicalGenere>>() {
            @Override
            public void onResponse(Call<List<MusicalGenere>> call, Response<List<MusicalGenere>> response) {
                if (response.code() == 200) {
                    List<MusicalGenere> mList = new ArrayList<>();
                    mList = response.body();
                    mlistGeneres.setValue(mList);
                    Log.d("getUserGeneres", "Number of instruments: " + mList.size());
                } else {
                    mlistGeneres.setValue(new ArrayList<MusicalGenere>());
                    Log.d("getUser", "Error " + response.code() + " message:" + response.message());
                    Log.d("getUser", "header es: " + auth);

                }
            }

            @Override
            public void onFailure(Call<List<MusicalGenere>> call, Throwable t) {
                Log.d("getInstrumentUserList", t.getMessage().toString());
            }
        });
    }


}
