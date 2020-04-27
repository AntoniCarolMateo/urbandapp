package cat.udl.urbandapp.services;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.models.Instrument;
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
    public final MutableLiveData<Boolean> mInstrumentAdded;
    public final MutableLiveData<Boolean> mRemovedInstrument;


    public TablesServiceImpl() {
        tablesDAO = new TablesDAOImpl();
        mUser = new MutableLiveData<>();
        mlistInstruent = new MutableLiveData<>();
        mInstrumentAdded = new MutableLiveData<>();
        mRemovedInstrument = new MutableLiveData<>();

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
    public MutableLiveData<Boolean> getLiveDataAddedIns() {
        return mInstrumentAdded;
    }

    @Override
    public MutableLiveData<Boolean> getLiveDataRemoveIns() {
        return mRemovedInstrument;
    }


    @Override
    public void getTableUserInstrument(final String Auth) {
        tablesDAO.getTableUserInstrument(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        //TODO: MIRAR COMO COGER LA LISTA CORRECTAMENTE
                        String respuestaBody = response.body().string();
                        JSONArray mIstruments = new JSONArray(respuestaBody);
                        List<Instrument> mList = new ArrayList<>();
                        for (int i = 0; i < mIstruments.length(); i++) {
                            JSONObject mInstrumentJson =  mIstruments.getJSONObject(i);
                            Instrument ins = new Instrument();

                            ins.setNameInstrument(mInstrumentJson.getString("name"));
                            ins.setExpirience(mInstrumentJson.getInt("expirience"));
                            mList.add(ins);
                        }
                        mlistInstruent.setValue(mList);


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    mlistInstruent.setValue(new ArrayList<Instrument>());
                    Log.d("getUser", "Error " + response.code() + " message:" + response.message());
                    Log.d("getUser", "header es: " + Auth);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getInstrumentUserList", t.getMessage().toString());
            }
        });
    }


    @Override
    public void addInstrument(String Auth, JsonObject instrument) {
        tablesDAO.addInstrument(Auth, instrument).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){
                    mInstrumentAdded.setValue(true);
                    Log.d("Add Instrument", "Added succesfully");
                }else{
                    mInstrumentAdded.setValue(false);
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
                    mRemovedInstrument.setValue(true);
                    Log.d("Remove Instrument", "Removed succesfully");
                }else{
                    mRemovedInstrument.setValue(false);
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
    public void addGenere(String auth, String nameGenere) {

    }

    @Override
    public void removeMultipleGeneres(String auth, List<String> list_generes) {

    }

    @Override
    public void removeGenere(String auth, String nameGenere) {

    }

    @Override
    public void getTableUserGenere(String auth) {

    }


}
