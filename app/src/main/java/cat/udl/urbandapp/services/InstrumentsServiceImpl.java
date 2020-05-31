package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.dao.InstrumentsDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstrumentsServiceImpl {

    private final String TAG = getClass().getSimpleName();

    private InstrumentsDAOImpl instrumentsDAO;

    public MutableLiveData<List<Instrument>> allInsruments;
    public final MutableLiveData<List<Instrument>> mlistInstruent;
    public final MutableLiveData<Boolean> mResponseWorked;
    public final MutableLiveData<User> mUser;

    public InstrumentsServiceImpl(){
        instrumentsDAO = new InstrumentsDAOImpl();
        mUser = new MutableLiveData<>();
        mlistInstruent = new MutableLiveData<>();
        mResponseWorked = new MutableLiveData<>();
    }

    public void getTableUserInstrument(final String Auth) {
        instrumentsDAO.getTableUserInstrument(Auth).enqueue(new Callback<List<Instrument>>() {
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



    public void addInstrument(String Auth, List<Instrument> instrument) {
        instrumentsDAO.addInstrument(Auth, instrument).enqueue(new Callback<ResponseBody>() {
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
                Log.d("Add Instrument", t.getMessage());

            }
        });
    }


    public void removeInstrument(String Auth, String instrument) {
        instrumentsDAO.removeInstrument(Auth, instrument).enqueue(new Callback<ResponseBody>() {
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

    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }
    public LiveData<List<Instrument>> getTableInstruments() {
        return mlistInstruent;
    }
    public MutableLiveData<Boolean> getLiveWorkedOrNot() {
        return mResponseWorked;
    }
}
