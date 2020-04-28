package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import java.util.ArrayList;

import com.google.gson.JsonObject;


import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;

import cat.udl.urbandapp.services.TablesServiceI;
import cat.udl.urbandapp.services.TablesServiceImpl;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {
    private UserServiceI repository;
    private TablesServiceI tablesRepository;
    private MutableLiveData<String> responseLiveDataToken = new MutableLiveData<>();
    private UserViewModel userViewModel;
    private MutableLiveData<User> responseLiveUser;
    private LiveData<List<Instrument>> mInstruments;
    private MutableLiveData<Boolean> responseAddedInstrument;
    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();

    public TablesViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();
        userViewModel = new UserViewModel(getApplication());
        tablesRepository = new TablesServiceImpl();
        responseLiveUser = repository.getLiveDataUser();
        responseAddedInstrument = tablesRepository.getLiveDataAddedIns();

        mInstruments = Transformations.switchMap(responseLiveDataToken, new Function<String, LiveData<List<Instrument>>>() {
            @Override
            public LiveData<List<Instrument>> apply(String input) {
                mInstruments = tablesRepository.getTableInstruments();
                return mInstruments;
            }

        });

    }

    public void addInstrument(String nameInstrument, int exp){
        Instrument ins = new Instrument(nameInstrument, exp);
        Toast.makeText(getApplication(), ins.toString(), Toast.LENGTH_SHORT).show();
        JsonObject jsonIns = ins.toJson();
        String header = this.mPreferences.getString("token","");
        tablesRepository.addInstrument(header, jsonIns);
    }

    public  void removeInstrument(Instrument instrument){
        String nameInstrument = instrument.getNameInstrument();
        Toast.makeText(getApplication(), nameInstrument, Toast.LENGTH_SHORT).show();
        String header = this.mPreferences.getString("token","");
        tablesRepository.removeInstrument(header, nameInstrument);
        Toast.makeText(getApplication(), nameInstrument + " Removed ", Toast.LENGTH_SHORT).show();
    }

    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }

    public LiveData<List<Instrument>> getInstruments(){
        return mInstruments;
    }

    public MutableLiveData<Boolean> getResponseAddedInstrument() { return responseAddedInstrument;}

    public void getListInstruments() {
        String header = this.mPreferences.getString("token","");
         tablesRepository.getTableUserInstrument(header);
    }
}
