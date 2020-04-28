package cat.udl.urbandapp.services;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;

import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cat.udl.urbandapp.preferences.PreferencesProvider;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TablesServiceImpl implements TablesServiceI {
    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();
    private TablesDAOImpl tablesDAO;

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    public final MutableLiveData<User> mUser;
    public final MutableLiveData<List<Instrument>> mlistInstrument;
    public final MutableLiveData<Boolean> mInstrumentAdded;
    public final MutableLiveData<Boolean> mRemovedInstrument;


    public TablesServiceImpl() {

        tablesDAO = new TablesDAOImpl();
        mUser = new MutableLiveData<>();
        mlistInstrument = new MutableLiveData<>();
        mInstrumentAdded = new MutableLiveData<>();
        mRemovedInstrument = new MutableLiveData<>();

    }

    @Override
    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }

    @Override
    public MutableLiveData<List<Instrument>> getTableInstruments() {
        return mlistInstrument;
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
                        JSONArray mInstruments = new JSONArray(respuestaBody);
                        Log.d("listInstruments", mInstruments.toString());
                        List<Instrument> mList = new ArrayList<>();

                        for (int i = 0; i > mInstruments.length(); i++) {
                            Log.d("keloke", "keloke");
                            JSONObject mInstrumentJson =  mInstruments.getJSONObject(i);
                            Instrument ins = new Instrument();
                            Log.d("listInstruments", ins.getNameInstrument() +" "+ ins.getExpirience());

                            ins.setNameInstrument(mInstrumentJson.getString("instrument"));
                            ins.setExpirience(mInstrumentJson.getInt("expirience"));
                            mList.add(ins);
                        }
                        mlistInstrument.setValue(mList);
                        Log.d("listInstruments", "cool   " + mlistInstrument.toString());


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    mlistInstrument.setValue(new ArrayList<Instrument>());
                    Log.d("getInstruments", "Error " + response.code() + " message:" + response.message());
                    Log.d("getInstruments", "header es: " + Auth);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getInstrumentUserList", t.getMessage().toString());
            }
        });
    }


    @Override
    public void addInstrument(String Auth, List<Instrument> instrument) {
        tablesDAO.addInstrument(Auth, instrument).enqueue(new Callback<Instrument>() {
            @Override
            public void onResponse(Call<Instrument> call, Response<Instrument> response) {
                if (response.code() == 200){
                    mInstrumentAdded.setValue(true);
                    Log.d("Add Instrument", "Added succesfully");
                }else{
                    mInstrumentAdded.setValue(false);
                    Log.d("Add Instrument", "Failed to Add instrument");
                }
            }

            @Override
            public void onFailure(Call<Instrument> call, Throwable t) {
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
    public void addMultipleGeneres(String auth, List<String> list_generes) {

    }


    @Override
    public void removeGenere(String auth, String nameGenere) {

    }

    @Override
    public void getTableUserGenere(String auth) {

    }


}
