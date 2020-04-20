package cat.udl.urbandapp.services;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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


    public TablesServiceImpl() {
        tablesDAO = new TablesDAOImpl();
        mUser = new MutableLiveData<>();
        mlistInstruent = new MutableLiveData<>();

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
    public void getTableUserInstrument(final String Auth) {
        tablesDAO.getTableUserInstrument(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        Gson gson = new Gson();
                        Type tableType = new TypeToken<Instrument>() {}.getType();
                        ArrayList<Instrument> user_instruments = gson.fromJson(response.body().string(), tableType);
                        User user = new User();
                        user.setUserInstruments(user_instruments);
                        List<Instrument> aux_list = user_instruments;

                        mUser.setValue(user);
                        mlistInstruent.setValue(aux_list);
                        Log.d("getTable", "----------Todo ok" + aux_list.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    mUser.setValue(new User());
                    Log.d("getUser", "Error en la call a la API llamada retornada con codigo" + response.code() + " message:" + response.message() );
                    Log.d("getUser", "header es: " + Auth);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    public void addInstrument(String Auth, JsonObject instrument) {
        tablesDAO.addInstrument(Auth, instrument);
    }

    @Override
    public void removeInstrument(String Auth, JsonObject instrument) {
        tablesDAO.removeInstrument(Auth, instrument);
    }



}
