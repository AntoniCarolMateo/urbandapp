package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {
    private UserServiceI repository;
    private UserViewModel userViewModel;
    private MutableLiveData<User> responseLiveUser;
    private ArrayList<Instrument> instrumentsTable;
    private SharedPreferences mPreferences;

    public TablesViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();
        userViewModel = new UserViewModel(getApplication());
        responseLiveUser = repository.getLiveDataUser();
        this.mPreferences = PreferencesProvider.providePreferences();

        //loadData();
    }

    /*public void loadData(){
        Gson gson = new Gson();
        String json = mPreferences.getString("tableInstruments", null);
        Type type = new TypeToken<ArrayList<Instrument>>() {}.getType();
        instrumentsTable = gson.fromJson(json, type);
    }*/

   /* public void removeData(){
        mPreferences.edit().remove("tableInstruments").apply();
    }*/


    public void addInstrument(String nameInstrument, int exp){
        Toast.makeText(getApplication(), nameInstrument + "   " + exp, Toast.LENGTH_SHORT).show();
        Instrument ins = new Instrument(nameInstrument, exp);
       /* trumentsTable.add(ins);
        Gson gson = new Gson();
        String arraylist = gson.toJson(instrumentsTable);
        mPreferences.edit().putString("tableInstruments",arraylist).apply();*/
        repository.setTableUserInstrument(ins);
    }
    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }

    public void setChanges(){


    }

}
